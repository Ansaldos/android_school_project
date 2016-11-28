package com.example.legye.wouldyourather.dataaccess;

/**
 * Created by legye on 2016. 11. 23..
 */

public class StaticResources {

    /**
     * Http request timeout
     */
    public static final int HTTP_TIMEOUT = 30000;

    /**
     * API base url
     */
    public static final String BASE_API_URL = "http://anss.myartsonline.com/api";

    /**
     * API key
     */
    public static final String API_KEY = "?key=WP0ueO3XMsNVhjsN";

    /**
     * Admin fragment password
     */
    public static final String ADMIN_PASSWORD = "123456";

    /**
     * Build API url with params
     * @param service Service identifier
     * @return Builded (full) url
     */
    public static final String buildUrl(String service)
    {
        StringBuilder buildedUrl = new StringBuilder(BASE_API_URL);
        if(service != null)
        {
            buildedUrl.append(service);
        }

        buildedUrl.append(API_KEY);

        return buildedUrl.toString();
    }

    /**
     * Build API url with params
     * @param service Service identifier
     * @param serviceParam Service parameter
     * @return Builded (full) url
     */
    public static final String buildUrl(String service, int serviceParam)
    {
        StringBuilder buildedUrl = new StringBuilder(BASE_API_URL);
        if(service != null)
        {
            buildedUrl.append(service);
        }

        if(serviceParam != 0)
        {
            buildedUrl.append("/" + serviceParam);
        }

        buildedUrl.append(API_KEY);

        return buildedUrl.toString();
    }

}
