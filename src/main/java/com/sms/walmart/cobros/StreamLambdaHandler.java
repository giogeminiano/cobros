package com.sms.walmart.cobros;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.sms.walmart.cobros.jobConfiguration.JobInit;

public class StreamLambdaHandler implements RequestStreamHandler {
  

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
            JobInit jobInit = new JobInit();
            jobInit.jobOffline();
    }
}