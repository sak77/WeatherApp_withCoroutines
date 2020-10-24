# WeatherApp_withCoroutines

The gist of this project is to use Kotlin Coroutines with Retrofit to fetch API data.
Some points to note -
Coroutines are Kotlin's API to perform background tasks. Alternatives to Rxjava for example.
In this case, Retrofit API request returns instance of Deferred instead of Call.
The Deferred instance is used with async() coroutine builder and its result is fetched via await().
withContext() is a method to switch between threads during execution of a coroutine.
