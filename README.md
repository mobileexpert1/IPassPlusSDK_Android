# IPassPlusSDK_Android
Steps of Using IPass SDK
To explain how a user can use the IPass SDK framework in steps, you can outline the process as follows:

Steps to Use IPass SDK :
Integrate of SDK into App
In this steps User Will add the IPass SDK inside the project's write: implementation 'com.github.mobileexpert1:IPassPlusSDK_Android:1.0.3'
    Add:- id ("maven-publish")
Configure Permissions in manifest file
In this step user will give required permissions in manifest file to enable the necessary device features:

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-permission android:name="android.permission.NFC" />


Initialise Database
This method is used to setup database. It will download necessary files required for processing.

    IPassSDK.initiliazeDatabase(this) { message ->

        println(message)
        
            }

Get Scenarios List :
This method returns list of scenarios available.

    IPassSDK.getScenariosList()


Show scanner :
This method opens the scanner to scan the document. It uploads the document on server for processing the data.

    IPassSDK.showScannerRequest(requireContext(), binding.root as mailto:viewgroup,"abc@gmail.com") { }