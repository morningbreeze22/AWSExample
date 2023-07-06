# AWSExample


For this exercise, I implemented these endpoints  

## upload file to S3

**URL** : `/upload`

**Method** : `Post`

**REQUEST** :
in header:
  Content-Type: multipart/form-data
in body:
  file : {file you want to upload}

**Success Response**

**Code** : `201 CREATED`

## find file path with etag

**URL** : `/etag/{etag}`

**Method** : `Get`

**Success Response**

**Code** : `200 OK`

## delete file by etag

**URL** : `/etag/{etag}`

**Method** : `Delete`

**Success Response**

**Code** : `200 OK`
