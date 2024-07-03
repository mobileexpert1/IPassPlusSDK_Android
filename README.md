![Logo](https://github.com/yazanalqasem/iPass2.0NativeiOS/blob/liveness_update/git_im.png)



[![License](https://img.shields.io/badge/License-Commercial-blue.svg)](LICENSE)
[![iPass](https://img.shields.io/badge/iPass-lightgreen)](https://ipass-mena.com)
![API Doc](https://img.shields.io/badge/API%20doc-v4.10.0-green)
![Swift](https://img.shields.io/badge/Swift-3x,%204x,%205x-red.svg)
![platform](https://img.shields.io/badge/Platforms-ios-orange)
![Swift Package Manager](https://img.shields.io/badge/Swift%20Package%20manager-compatible-orange)
![pod](https://img.shields.io/badge/pod-v4.10.0-yellow)

# Table of Contents
- [Overview](#overview)
- [Get Started](#steps-of-using-ipass-ios-package)
  - [Integrate Package into the App](#integrate-package-into-the-app)
  - [Import Package](#import-package)
  - [General Requirements](#import-package)
  - [Permissions](#permissions)
  - [Add NFC Compatibility](#add-nfc-compatibility)
  - [Initialize Database](#initialize-database)
  - [Get User Login Token](#get-user-login-token)
  - [Get Supported Flows](#get-supported-flows)
  - [Document Scanning](#document-scanning)
  - [Add Delegate to Get Response](#add-delegate-to-get-response)
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

## Steps of using iPASS iOS Package

To explain how a user can use the iPASS Package in steps, you can outline the process as follows:

## Steps to use iPASS iOS Package

### Integrate Package into the App

First, we need to add the package to the application using the git link: [https://github.com/yazanalqasem/iPass2.0NativeiOS](https://github.com/yazanalqasem/iPass2.0NativeiOS)

Steps to add the package:
1. Go to `Files > Add Package Dependencies`
2. Paste the Package URL
3. Click on "Add Package" to include the package in your project.

-----

### Import Package

Import the package in your required view controller.

```ruby
import iPass2_0NativeiOS
```
-----

# Permissions
### Configure Permissions in Info.plist

In this step user will give required permissions in the Info.plist file to enable the necessary device features:

- Privacy - Camera Usage Description — Description for camera access.
- Privacy - Photo Library Usage Description - Description for photo library access.
- Privacy - Bluetooth Always Usage Description - Description for Bluetooth access.
- Privacy - Speech Recognition Usage Description - Description for speech recognition.
- Privacy - Privacy - NFC Scan Usage Description - Description for NFC usage.
- ISO7816 application identifiers for NFC Tag Reader Session - For NFC features.
  

Add following items to above array item
- A0000002471001
- E80704007F00070302
- A000000167455349474E
- A0000002480100
- A0000002480200
- A0000002480300
- A00000045645444C2D3031
- com.apple.developer.nfc.readersession.formats. - NFC reading formats

Add following items to above array item
- NDEF
- TAG

-----

### Add NFC Compatibility
- Near Field Communication Tag Reading
-----
### Initialize Database
- To start the process user need to download the database using following code.
- In this step progress object can be used to track the downloading percentage.
- Once the database is downloaded 100% and status is Start Now, user can start the next step.
```ruby
 DataBaseDownloading.initialization(completion:{progres, status, error in
                    print(progres, status, error)
                })
```
-----

### Get User Login Token
- Pass valid email id and password to get user token
```ruby
iPassSDKManger.UserOnboardingProcess(email: emailStr, password: passwordStr) { status, tokenString in
    if(status == true) {
        self.userToken = tokenString!
    }
}
```
- Once the user is logged in user token need to save because this will be used in document scanning process

-----
  
### Get Supported Flows
```ruby
    var getList = [[String: Any]]()
    getList = iPassSDKManger.getWorkFlows()
```
- In the getList object you will get the array of dictionary with all the flows
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

 ```ruby
iPassSDKManger.delegate = self
iPassSDKManger.startScanningProcess(userEmail: "sam@gmail.com", flowId: 10031, socialMediaEmail :"samfb@gmail.com", phoneNumber : "978xxxxxx", controller: self, userToken: self.userToken, appToken: self.appToken)
```
- usertoken will be the login token
- appToken will be the auth token provided by Admin
- flowId will be the id selected by the user from above step.
- After the scanning process, Response will be available in package delegate.
-----

### Add Delegate to Get Response
 ```ruby
   extension ViewController : iPassSDKManagerDelegate {
        func getScanCompletionResult(result: String, transactionId: String, error: String) {
            print(result)
            print(transactionId)
            print(error)
        }
    }
```
- "result" object will return the required json response
- "transactionId" object will return the unique transcation number for completed transaction
- "error" object will return the error description
-----

### SDK Properties
 ```ruby
configProperties.setLoaderColor(color: UIColor.red)
configProperties.needHologramDetection(value: true)
configProperties.needHologramDetection(value: true)
configProperties.setDateFormat(format: "dd/mm/yyyy")
```
- "setLoaderColor" property is used to change the color of the loader.
- "needHologramDetection" property is used for Authenticity checks like detection of Electronic Device, Optically Variable Ink, Multiple Laser Images, Image Patterns.
- By default hologram detection is enabled. you can disabled passing the false in hologram detection property.
- "setDateFormat" property is used to change the format of dates displayed in the results. The mask examples are "dd/mm/yyyy", "mm/dd/yyyy", "dd-mm-yyyy", "mm-dd-yyyy", "dd/mm/yy".
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

You need to add the multi languages files from the xcode for requried languages and you can copy the language keys from the following link

 <a href="https://devdocs.ipass-mena.com/ipass-sdkLanguages.html">
   Language Files
  </a>

-----

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




