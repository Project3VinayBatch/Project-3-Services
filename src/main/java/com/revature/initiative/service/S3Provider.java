package com.revature.initiative.service;

import com.amazonaws.services.s3.AmazonS3;

public interface S3Provider {
    AmazonS3 provide();

    String getBucket();
}
