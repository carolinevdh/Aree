Aree
====

is a...

J2EE framework for creation of modular cloud-enabled applications: using CDI, Dynamic Classloading and RESTful communication.


More? Sure!

This Java EE framework allows for the dynamic chaining of third-party components, creating a modular program accessible from the cloud. In the developed use case, a cross-platform iPad application (see github.com/carolinevdh/Aree3DChemistryApp) configures its instance of the framework with libraries that can access an SQL database with knowledge about chemical models, and libraries that can reason about these models. It leverages the framework to display and have 3D models of chemical molecules react in an Augmented Reality environment. The framework typically receives an XML descriptor from the client iPad, parses this configuration and dynamically loads the requested libraries, when found on the TomEE server. Once all libraries have been found and chained together, the iPad application uses simple RESTful requests to use its specific configuration on the framework, benefiting from the server's processing power, load balancing and fault tolerance features.


Key technologies used:
Java EE 6 and its Contexts and Dependency Injection, REST, Java SE 7 WatchService, Apache TomEE

The use case iPad app at github.com/carolinevdh/Aree3DChemistryApp also uses:
PhoneGap, Wikitude SDK (for Augmented Reality), Qualcomm Vuforia (for image recognition), JavaScript, Three.js (for CSS3D)


(This framework was developed as part of a Masters dissertation, and thus also contains load and speed testing code)
