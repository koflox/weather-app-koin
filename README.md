# Weather application

Just a sample of client server application designed for the weather search.
 
This Android app is based on MVVM architecture and written in Kotlin via Android SDK.

There are two options to try this app:
 - build it on your own, you will need to past the missing API keys, 
      one for a weather data - [OpenWeatherMap API](https://openweathermap.org/api)
      and another one for a city icons (not used at the moment) - [Pixabay API](https://pixabay.com/api/docs/)
 - install apk from the [sample folder](/sample)
 
### Technologies used
  - Android Architecture Components: ViewModel, LiveData, DataBinding, NavigationComponent etc.
  - Retrofit + OkHttp for network comunication
  - Room for storing data in the local source
  - Glide as image loader library
  - Koin as dependency injection framework
  - Kotlin coroutines for long-running operation like network calls and i/o operations
  
 ### General todo list
  - add multilanguage support
  - add weather support for the current location
  - implement weather auto update for favorite places
 
 ### Code todo list
  - review gradle configuration, some library version declaratinos duplicated
  - review databinding implementation, extract base view holder class or use third-party databinding library like https://github.com/evant/binding-collection-adapter
  - add scrolling to GraphicView, handle resize events etc.
 
 ### Ui todo list
  - add places suggestions
  - implement custom SearchView
  - calculate missing weather data values, e.g. feels-like temp, visibility etc.
  - review favorite places screen ui
  - implement settings screen ui
