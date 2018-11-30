package bg.adastragrp.adastrafaceapp.data.data_sources;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import bg.adastragrp.adastrafaceapp.constants.Constants;
import bg.adastragrp.adastrafaceapp.data.models.User;
import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class UsersRemoteDataSource {

    public @Inject UsersRemoteDataSource() {
    }

    public Single<List<User>> fetchUsers() {
        DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DB_USERS);

        return Single.create(emitter -> {
            firebaseDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<User> users = new ArrayList<>();
                    for (DataSnapshot eachUserId : dataSnapshot.getChildren()) {
                        User user = eachUserId.getValue(User.class);
                        user.setId(eachUserId.getKey());

                        users.add(user);
                    }

                    emitter.onSuccess(users);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            });
        });
    }

    public Single<User> loginUser(String email, String password) {

        return Single.create(emitter -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // fetch my user and save it in the local DB
                            String loggedUserId = task.getResult().getUser().getUid();
                            DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference(
                                    Constants.FIREBASE_DB_USERS + "/" + loggedUserId);

                            firebaseDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (user == null) {
                                        user = new User();
                                    }
                                    user.setId(loggedUserId);
                                    emitter.onSuccess(user);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    emitter.onError(databaseError.toException());
                                }
                            });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Completable registerUser(User user, Bitmap avatarImage) {

        return Completable.create(emitter -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (avatarImage != null) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            avatarImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            String avatarPath = Constants.FIREBASE_USERS_PROFILE_IMAGES_BUCKET_NAME + "/" + UUID.randomUUID();
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference avatarStorageReference = storage.getReference(avatarPath);

                            UploadTask uploadTask = avatarStorageReference.putBytes(data);
                            uploadTask
                                    .addOnSuccessListener(taskSnapshot ->
                                            avatarStorageReference.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DB_USERS);

                                                        user.setPassword(null);// do not save password in users document
                                                        user.setAvatarUrl(uri.toString());
                                                        user.setAvatarShortPathName(avatarPath);
                                                        FirebaseUser registeredUser = task.getResult().getUser();
                                                        String userId = registeredUser.getUid();
                                                        user.setId(userId);
                                                        Long userCreatedDate = registeredUser.getMetadata() != null
                                                                ? registeredUser.getMetadata().getCreationTimestamp()
                                                                : null;
                                                        user.setDateUserCreated(userCreatedDate);
                                                        firebaseDB.child(userId).setValue(user)
                                                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                                                .addOnFailureListener(exception -> emitter.onError(exception));
                                                    })
                                                    .addOnFailureListener(exception -> emitter.onError(exception)))
                                    .addOnFailureListener(exception -> emitter.onError(exception));
                        } else {
                            DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DB_USERS);

                            user.setPassword(null);// do not save password in users document
                            FirebaseUser registeredUser = task.getResult().getUser();
                            String userId = registeredUser.getUid();
                            user.setId(userId);
                            Long userCreatedDate = registeredUser.getMetadata() != null
                                    ? registeredUser.getMetadata().getCreationTimestamp()
                                    : null;
                            user.setDateUserCreated(userCreatedDate);
                            firebaseDB.child(userId).setValue(user)
                                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                                    .addOnFailureListener(exception -> emitter.onError(exception));
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                });
        });
    }

    public Single<User> updateUser(User user, Bitmap avatarImage) {
        DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DB_USERS);

        return Single.create(emitter -> {

            if (avatarImage != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                avatarImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                String avatarPath = Constants.FIREBASE_USERS_PROFILE_IMAGES_BUCKET_NAME + "/" + UUID.randomUUID();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference avatarStorageReference = storage.getReference(avatarPath);

                if (!TextUtils.isEmpty(user.getAvatarUrl()) && !TextUtils.isEmpty(user.getAvatarShortPathName())) {
                    // upload new picture
                    UploadTask uploadTask = avatarStorageReference.putBytes(data);
                    uploadTask
                            .addOnSuccessListener(taskSnapshot ->
                                    avatarStorageReference.getDownloadUrl()
                                            .addOnSuccessListener(uri -> {
                                                // set new url to user
                                                String previousAvatarShortName = user.getAvatarShortPathName();

                                                user.setAvatarUrl(uri.toString());
                                                user.setAvatarShortPathName(avatarPath);
                                                // update user in the database
                                                firebaseDB.child(user.getId()).setValue(user)
                                                        .addOnSuccessListener(aVoid -> {
                                                            // delete old image
                                                            StorageReference storageReference = storage.getReference();
                                                            StorageReference previousAvatarReference = storageReference.child(previousAvatarShortName);

                                                            previousAvatarReference.delete();

                                                            emitter.onSuccess(user);
                                                        })
                                                        .addOnFailureListener(exception -> emitter.onError(exception));
                                            })
                                            .addOnFailureListener(exception -> emitter.onError(exception)))
                            .addOnFailureListener(exception -> emitter.onError(exception));
                } else {
                    UploadTask uploadTask = avatarStorageReference.putBytes(data);
                    uploadTask
                            .addOnSuccessListener(taskSnapshot ->
                                    avatarStorageReference.getDownloadUrl()
                                            .addOnSuccessListener(uri -> {
                                                // set new url to user
                                                user.setAvatarUrl(uri.toString());
                                                user.setAvatarShortPathName(avatarPath);
                                                firebaseDB.child(user.getId()).setValue(user)
                                                        .addOnSuccessListener(aVoid -> emitter.onSuccess(user))
                                                        .addOnFailureListener(exception -> emitter.onError(exception));
                                            })
                                            .addOnFailureListener(exception -> emitter.onError(exception)))
                            .addOnFailureListener(exception -> emitter.onError(exception));
                }
            } else {
                firebaseDB.child(user.getId()).setValue(user)
                        .addOnSuccessListener(aVoid -> emitter.onSuccess(user))
                        .addOnFailureListener(exception -> emitter.onError(exception));
            }
        });
    }
}