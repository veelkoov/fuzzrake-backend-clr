package web.client

import io.github.oshai.kotlinlogging.KotlinLogging
import web.snapshots.Snapshot
import web.url.Url

private val logger = KotlinLogging.logger {}

class CookieEagerHttpClient(private val client: HttpClientInterface) : HttpClientInterface {
    private val prefetched = mutableSetOf<String>()

    override fun get(url: Url): Snapshot {

        url.getStrategy().getCookieInitUrl()?.let { cookieUrl ->
            if (!prefetched.contains(cookieUrl.getUrl())) {
                logger.info("${url.getUrl()} requires prefetch of ${cookieUrl.getUrl()}")

                client.get(cookieUrl)
                prefetched.add(cookieUrl.getUrl())
            }
        }

        return client.get(url)
    }
}
