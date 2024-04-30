# IPassPlusSDK_Android

Steps of Using IPass SDK
To explain how a user can use the IPass SDK framework in steps, you can outline the process as follows:

-> Integrate of SDK into App
In this step User Will add the IPass SDK inside the project's write:
    
    implementation 'com.github.mobileexpert1:IPassPlusSDK_Android:2.0.6'

Add:-
    
    id ("maven-publish")

Add these lines in your settings.gradle file

    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            jcenter()
            maven {
                url =uri("https://maven.regulaforensics.com/RegulaDocumentReader/Beta")
            }
        }
    }

Configure Permissions in manifest file

In this step user will give required permissions in manifest file to enable the necessary device features:

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-permission android:name="android.permission.NFC" />


*************************************

* Initialise Database:

This method is used to setup database. It will download necessary files required for processing.

        //  Initializing
        IPassSDK.initializeDatabase(context, object: InitializeDatabaseCompletion {
                    override fun onProgressChanged(progress: Int) {
                        // get progress
                    }
        
                    override fun onCompleted(
                        status: Boolean,
                        message: String?
                    ) {
                        //Initialization Complete

                    }
                })


* Get Auth Token :

This method verfies the account credentials and returns a Authorization token which is valid for 30 minutes. This token is required in next steps for authorization.

        IPassSDK.getAuthToken(context, "email", "password", object : ResultListener<LoginResponse> {
            override fun onSuccess(response: LoginResponse?) {
                val authToken = response?.user?.token!!
                // Get auth token
            }

            override fun onError(exception: String) {
                // show error message
            }
        })


* Get Scenarios List :

This method returns list of scenarios available.

        IPassSDK.getScenariosList()


* Show scanner :

This method opens the scanner to scan the document. It uploads the document on server for processing the data.

        IPassSDK.showScannerRequest(requireContext(),email, authToken, token, ViewGroup) {
            status, message ->
            if (status) {
                // show success message
            } else {
                // show error message message
            }
        }


* Get Document Data :

This Method Returns data scanned from Documents.

        IPassSDK.getDocumentScannerData(requireContext(), Constants.TOKEN, object : ResultListener<DocumentScannerResponse> {
            override fun onSuccess(response: DocumentScannerResponse?) {
        //   Get Document Scanner Data
        }

            override fun onError(exception: String) {
        //   Show error
        }

        })    


* Get Face Data :

This method processes face images and it returns Face liveness data and Face Similarity Data.

        IPassSDK.getFaceScannerData(requireContext(), Constants.TOKEN, object : ResultListener<FaceScannerResponse> {
            override fun onSuccess(response: FaceScannerResponse?) {
        //   Get Face Scanner Data
        }

            override fun onError(exception: String) {
        //    Show error
        }
        })
    

************************