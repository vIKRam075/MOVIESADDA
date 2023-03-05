package com.example.pipapplication.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor/*, Authenticator*/ {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

//            requestBuilder?.addHeader("Content-Type", "application/json")
//            requestBuilder?.addHeader("Accept", "application/json")

        // If token has been saved, add it to the request
        /*PreferenceManager.getAuthToken()?.let {
            requestBuilder.addHeader("Authorization", it)
        }
*/
        return chain.proceed(requestBuilder.build())
    }


    /**
     * Authenticator for when the authToken need to be refresh and updated
     * everytime we get a 401 error code
     */
/*    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        var requestAvailable: Request? = null
        try {
            requestAvailable = response?.request?.newBuilder()
                ?.addHeader("Authorization", PreferenceManager.getAuthToken().toString())
                ?.build()
            return requestAvailable
        } catch (ex: Exception) {
        }
        return requestAvailable
    }*/
}
