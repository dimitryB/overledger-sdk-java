# overledger-sdk-api

API module contains interfaces which are used as guideline for helping SDK implementation

## Maven dependency:

```xml
<dependency>
    <groupId>io.overledger</groupId>
    <artifactId>overledger-sdk-api</artifactId>
    <version>1.0.0-alpha</version>
</dependency>
```

## io.overledger.OverledgerContext

[OverledgerContext](./src/io/overledger/OverledgerContext.java) is designed for holding Overledger BPI key, Mapp ID and BPI endpoint constants.

OverledgerContext.loadContext() method loads following properties from Properties object or InputStream

```
overledger.baseurl=[Endpont base URL]
overledger.write=[Endpont for writing transaction]
overledger.read.id=[Endpont for reading transaction by transaction ID]
overledger.read.mappid=[Endpont for reading transaction by Mapp ID]
overledger.read.txhash=[Endpont for reading transaction by transaction hash]
overledger.bpikey=[BPI Key]
overledger.mappid=[Mapp ID]
```

## io.overledger.api.Account

[Account](./src/io/overledger/api/Account.java) interface is used for declaring basic account functions.

Overledger SDK uee Account to sign transactions

## io.overledger.api.Client

Implement [Client](./src/io/overledger/api/Client.java) interface to access BPI service with basic authorization.

Use OverledgerContext to get endpoint URLs, BPI key and Mapp ID

## io.overledger.api.OverledgerSDK

Interface [OverledgerSDK](./src/io/overledger/api/OverledgerSDK.java) is designed as entry point of the SDK.
Application access BPI layer through OverledgerSDK implementation.

OverledgerSDK also includes methods for collecting DLT accounts and client implementation. 
