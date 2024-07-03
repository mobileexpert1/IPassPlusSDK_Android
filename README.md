![Logo](https://github.com/yazanalqasem/iPass2.0NativeiOS/blob/liveness_update/git_im.png)



[![License](https://img.shields.io/badge/License-Commercial-blue.svg)](LICENSE)
[![iPass](https://img.shields.io/badge/iPass-lightgreen)](https://ipass-mena.com)
![API Doc](https://img.shields.io/badge/API%20doc-v2.12-green)
![Swift](https://img.shields.io/badge/Kotlin-1.9-red.svg)
![platform](https://img.shields.io/badge/Platforms-Android-orange)
![pod](https://img.shields.io/badge/sdk-v2.12-yellow)

# Table of Contents
- [Overview](#overview)
- [Get Started](#steps-of-using-ipass-android-package)
  - [Integrate Package into the App](#integrate-package-into-the-app)
  - [Permissions](#permissions)
  - [Add NFC Compatibility](#add-nfc-compatibility)
  - [Initialize Database](#initialize-database)
  - [Get User Login Token](#get-user-login-token)
  - [Get Supported Flows](#get-supported-flows)
  - [Document Scanning](#document-scanning)
  - [Get Document Data](#get-document-data)
  - [SDK Properties](#sdk-properties)
  - [Language Localization](#add-multiple-languages-optional)
- [Support](#support)
- [Licenses](#licenses)
- [Contact](#contact)
- [Copyright](#copyright)


#### Updates in new version
-  Updated user liveness

# Overview
AI-powered identity verification, eKYC, and
transaction monitoring

In today’s digital economy, fraudsters and money
launderers have no place. To avoid fraud and financial
crime, internet firms must know and trust that their
clients are who they say they are – and that they will
remain trustworthy. 

---

## Steps of using iPASS Android Package

To explain how a user can use the iPASS Package in steps, you can outline the process as follows:

## Steps to use iPASS Android Package

### Integrate Package into the App

In this step User Will add the IPass SDK inside the app's gradle file:

    implementation("com.github.yazanalqasem:iPass2.0NativeAndroidSDK:2.12")
    implementation("com.github.yazanalqasem:iPass2.0CoreAndroidSDK:2.1")


Add these lines in your settings.gradle file

    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            maven {
                url = uri("https://jitpack.io")
            }
            maven {
                url =uri("https://maven.regulaforensics.com/RegulaDocumentReader/Beta")
            }
        }
    }

-----

# Permissions

### Configure Permissions in manifest file

You need to specify required permissions in manifest file to enable the necessary device features:

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-permission android:name="android.permission.NFC" />
  

-----

### Add NFC Compatibility
- Check NFC is enabled in device settings
-----
### Initialize Database
- To start the process user need to download the database using following code.
- In this step progress object can be used to track the downloading percentage.
- Once the database is downloaded 100% and status returns true, user can start the next step.

        DataBaseDownloading.initialization(this, object: InitializeDatabaseCompletion {
            override fun onProgressChanged(progress: Int) {
                // get progress
            }

            override fun onCompleted(
                status: Boolean,
                message: String?
            ) {
                if (status) {
                    // Show message
                } else {
                    // Show error message
                }
            }

        })


-----

### Get User Login Token
- Pass valid email id and password to get user token

        iPassSDKManger.UserOnboardingProcess(context, email, password, object : ResultListener<AuthenticationResponse> {
            override fun onSuccess(response: AuthenticationResponse?) {
                val authToken = response?.user?.token!!
                // Get auth token
            }

            override fun onError(exception: String) {
                // show error message
            }
        })

- Once the user is logged in user token need to save because this will be used in document scanning process

-----
  
### Get Supported Flows

        iPassSDKManger.getScenariosList()

- Get the flowId from the list of supported flows which will be required for scanning process
- Sdk Supported Flows
  - Full Processing(10031)
  - Id verification + Liveness + AML(10032)
  - Id verification + AML(10015)
  - Id verification + Liveness(10011)
- In Full Process(10031), Social media email and phone number is required

### Document Scanning

- User can scan various types of documents.
- Users can scan both the front and back sides of documents, but it totally depends on the document type.

        iPassSDKManger.startScanningProcess(requireContext(), email, userToken, apptoken, socialMediaEmail, phoneNumber, flowId, ViewGroup) {
            status, message ->
            if (status) {
                // show success message
            } else {
                // show error message message
            }
        }

- usertoken will be the login token
- appToken will be the auth token provided by Admin
- flowId will be the id selected by the user from above step.
- After the scanning process, the response can be obtained from getDocumentScannerData method.
-----

### Get Document Data :

This Method Returns data scanned from Documents.

        iPassSDKManger.getDocumentScannerData(requireContext(), apptoken, object : ResultListener<TransactionDetailResponse> {
            override fun onSuccess(response: TransactionDetailResponse?) {
                if (response?.Apistatus!!) {
                    //   Get Document Scanner Data
                } else {
                    //   Show error
                }
            }

            override fun onError(exception: String) {
                //   Show error
            }

        })

- onSuccess - "response.data" object will return the required json response
- onError - "exception" will return the error message in String
-----

### SDK Properties
 ```ruby
configProperties.needHologramDetection(value: true)
configProperties.needHologramDetection(value: false)
```
- "needHologramDetection" property is used for Authenticity checks like detection of Electronic Device, Optically Variable Ink, Multiple Laser Images, Image Patterns.
- By default hologram detection is disabled. you can enable it by passing the true in hologram detection property.
-----

### Add Multiple Languages (Optional)
Applications supports following langauges
- Arabic
- English
- French
- German
- Kurdish
- Spanish
- Turkish
- Urdu

-----

### Reduce APK Size

To reduce the APK size, follow these steps:

  1. In android studio, Select File > New > New Module from the menu bar. In the Create New Module dialog, select Dynamic Feature Module and click Next.

  2. On the Configure your new module screen, give your module a name(iPassSdk).

  3. On the Configure your new module screen, specify the module title(iPass).

  4. Check the Enable on-demand box. Hit Finish and wait for the project to sync.

  5. Now add the below mentioned line in the dynamic module's (iPassSdk) build gradle file and sync project.

           implementation("com.github.yazanalqasem:iPass2.0CoreAndroidSDK:2.1")

     Note : Remove this line from app's build gradle file

  6. Add these lines in your activity

           var splitInstallManager: SplitInstallManager? = null

           splitInstallManager = SplitInstallManagerFactory.create(this)

           val request = SplitInstallRequest.newBuilder()
          .addModule(name)
          .build()

           splitInstallManager?.startInstall(request)
            ?.addOnSuccessListener {
           // Packages Installed (Initialise Database Here)
           }
           ?.addOnFailureListener {
           // Packages Installation failed!
           }

     
# Support
Please refer to our [support policy](https://ipass-mena.com/contact/) for more information about Mobile SDK support.


## Licenses
The software contains third-party open source software. For more information, please see [license](LICENSE).


## Contact
If you have any questions regarding our implementation guide please contact ipass-mena Customer Service. The ipass-mena online helpdesk contains a wealth of information regarding our service including demo videos, product descriptions, FAQs and other things that may help to get you started with ipass-mena.

## Copyright
&copy; Copyright (c) 2024 iPass

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.For SDK license key you need to contact on info@ipass-mena.com




