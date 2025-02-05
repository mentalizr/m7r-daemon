package org.mentalizr.scheduler;

import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.sessionManagement.SessionStatusService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.clientSdk.ClientSdkException;
import org.mentalizr.clientSdk.SessionAgent;
import org.mentalizr.serviceObjects.SessionStatusSO;
import org.mentalizr.serviceObjects.SessionStatusSOX;

import java.net.HttpCookie;

public class Dummy {

    public static void main(String[] args) {

        try {
            SessionAgent sessionAgent = SessionAgent.createFromLocalConfigWithTransientCookieStorage();
            RESTCallContext restCallContext = sessionAgent.getRESTCallContext();

            System.out.println("transient cookie? " + restCallContext.hasTransientCookie());
            System.out.println("nr of cookies in transient store: " + restCallContext.getM7rCookieStoreTransient().getCookies().size());
            HttpCookie httpCookie = restCallContext.getM7rCookieStoreTransient().getCookies().getFirst();
            System.out.println("name    : " + httpCookie.getName());
            System.out.println("value   : " + httpCookie.getValue());
            System.out.println("path    : " + httpCookie.getPath());
            System.out.println("version : " + httpCookie.getVersion());


            SessionStatusSO sessionStatusSO = new SessionStatusService(restCallContext).call();
            System.out.println(SessionStatusSOX.toJson(sessionStatusSO));

            sessionAgent.logout();

        } catch (ClientSdkException | RestServiceHttpException | RestServiceConnectionException e) {
            e.printStackTrace();
        }

    }

}
