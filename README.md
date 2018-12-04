# FaceApp

# Third party libraries used
- **Firebase**
Firebase is used for Register/Login and for saving of binary objects (user profile images). It is also used for a serverside database storage.
- **Butterknife**
Binding views and callbacks to respectively fields and methods. Simplifies process of views finding and attaching OnClick listeners.
- **RxJava2**
Used for reactive programming approach. It makes asynchronous and event-driven programming much easier process and only with couple of lines.
- **RxAndroid**
It provides a scheduler that executes tasks on the main thread or any given Looper.
- **Dagger2**
Dependency injection framework which makes dependency injection less prone to errors, memory leaks and makes some implementations like Singletons, one-liner.
- **Glide**
A library for images downloading/loading/caching.
- **Databinding**
Part of Architecure components, databinding provides us with nice features like data sources binding to views directly in XML. 
Cause its reactive nature, it compliments RxJava very well. 
It can also improve app's performance and help prevent memory leaks and null pointer exceptions.
- **Lifecycle components**
Perform actions in response to a change in the lifecycle status of other components, such as activities and fragments. 
These lifecycle components help to produce better-organized, and often lighter-weight code, that is easier to maintain.
- **Room**
Local database storage based on SQLite. 
Simpler, less boilerplate to write and will lot of functions like compile time SQL verification, simple conversion to POJO, LiveData results.
- **Dexter**
Runtime permissions requester and receiver.
- **Android-Image-Cropper**
Used for image cropping/rotating/scaling