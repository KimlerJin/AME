//package com.ame.util.net.minio;
//
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import org.apache.commons.pool2.BasePooledObjectFactory;
//import org.apache.commons.pool2.PooledObject;
//import org.apache.commons.pool2.impl.DefaultPooledObject;
//
//
//public class MinIOClientFactory extends BasePooledObjectFactory<MinioClient> {
//
//    private String endpoint;
//
//    private String bucket;
//
//    private String accessKey;
//
//    private String secretKey;
//
//
//    public MinIOClientFactory(String endpoint, String bucket, String accessKey, String secretKey) {
//        this.endpoint = endpoint;
//        this.bucket = bucket;
//        this.accessKey = accessKey;
//        this.secretKey = secretKey;
//    }
//
//    @Override
//    public MinioClient create() throws Exception {
//        MinioClient minioClient =
//                MinioClient.builder()
//                        .endpoint(endpoint)
//                        .credentials(accessKey, secretKey)
//                        .build();
//        // Check if the bucket exists; if not, create it
//        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
//        if (!bucketExists) {
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
//        }
//        return minioClient;
//    }
//
//    @Override
//    public PooledObject<MinioClient> wrap(MinioClient minioClient) {
//        return new DefaultPooledObject<MinioClient>(minioClient);
//    }
//}
