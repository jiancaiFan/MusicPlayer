package cn.happykum.musicplayer.utils.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkRequestUtil {

    private const val DEFAULT_TIMEOUT = 30L

    // 日志拦截器
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // OkHttp 客户端
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ================= 策略模式 =================

    private interface RequestStrategy {
        fun buildRequest(url: String, headers: Map<String, String>?, body: RequestBody? = null): Request
    }

    private object GetStrategy : RequestStrategy {
        override fun buildRequest(url: String, headers: Map<String, String>?, body: RequestBody?): Request {
            val builder = Request.Builder().url(url).get()
            headers?.forEach { (k, v) -> builder.addHeader(k, v) }
            return builder.build()
        }
    }

    private object PostStrategy : RequestStrategy {
        override fun buildRequest(url: String, headers: Map<String, String>?, body: RequestBody?): Request {
            requireNotNull(body) { "POST body required" }
            val builder = Request.Builder().url(url).post(body)
            headers?.forEach { (k, v) -> builder.addHeader(k, v) }
            return builder.build()
        }
    }

    private object PutStrategy : RequestStrategy {
        override fun buildRequest(url: String, headers: Map<String, String>?, body: RequestBody?): Request {
            requireNotNull(body) { "PUT body required" }
            val builder = Request.Builder().url(url).put(body)
            headers?.forEach { (k, v) -> builder.addHeader(k, v) }
            return builder.build()
        }
    }

    private object DeleteStrategy : RequestStrategy {
        override fun buildRequest(url: String, headers: Map<String, String>?, body: RequestBody?): Request {
            val builder = Request.Builder().url(url)
            if (body != null) builder.delete(body) else builder.delete()
            headers?.forEach { (k, v) -> builder.addHeader(k, v) }
            return builder.build()
        }
    }

    private fun getStrategy(method: String): RequestStrategy = when (method.uppercase()) {
        "GET" -> GetStrategy
        "POST" -> PostStrategy
        "PUT" -> PutStrategy
        "DELETE" -> DeleteStrategy
        else -> throw IllegalArgumentException("Unsupported method: $method")
    }

    // =============== 异步请求（Callback） ===============

    fun get(
        url: String,
        headers: Map<String, String>? = null,
        callback: Callback
    ) = requestAsync(url, "GET", null, headers, callback)

    fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        callback: Callback
    ) = requestAsync(url, "POST", jsonBody, headers, callback)

    fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        callback: Callback
    ) = requestAsync(url, "PUT", jsonBody, headers, callback)

    fun delete(
        url: String,
        headers: Map<String, String>? = null,
        callback: Callback
    ) = requestAsync(url, "DELETE", null, headers, callback)

    private fun requestAsync(
        url: String,
        method: String,
        jsonBody: String? = null,
        headers: Map<String, String>? = null,
        callback: Callback
    ) {
        val body = jsonBody?.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = getStrategy(method).buildRequest(url, headers, body)
        client.newCall(request).enqueue(callback)
    }

    // =============== 协程 suspend 封装 ===============

    suspend fun get(
        url: String,
        headers: Map<String, String>? = null,
    ): Response = requestSuspend(url = url, method = "GET", headers = headers)

    suspend fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
    ): Response = requestSuspend(url = url, method = "POST", jsonBody = jsonBody, headers = headers)

    suspend fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
    ): Response = requestSuspend(url = url, method = "PUT", jsonBody = jsonBody, headers = headers)

    suspend fun delete(
        url: String,
        headers: Map<String, String>? = null,
    ): Response = requestSuspend(url = url, method = "DELETE", headers = headers)

    suspend fun requestSuspend(
        url: String,
        method: String = "GET",
        jsonBody: String? = null,
        headers: Map<String, String>? = null
    ): Response = withContext(Dispatchers.IO) {
        val body = jsonBody?.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = getStrategy(method).buildRequest(url, headers, body)
        client.newCall(request).execute()
    }
}