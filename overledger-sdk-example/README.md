[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# overledger-sdk-example

This module gives a sample project of using Overledger SDK for Java.

This project use a simple Java Swing application to demonstrate how to use Overledger SDK for Java.
[OverledgerSDKHelper](./src/network/quant/sdk/OverledgerSDKHelper.java) contains all methods and parameters for accessing Overledger SDK.

## How to run example application

Run main method under [OverledgerEntryPoint](./src/network/quant/OverledgerEntryPoint.java)

## How to use example application

![Main page](../docs/example_001.png)

From main page, you can access all options over the top right corner of the GUI

![Settings page](../docs/example_002.png)

In settings page, you can setup endpoints for BPI level access. Or you could load properties file by dragging file into this panel.

![Wallet page](../docs/example_003.png)

In Wallet page, you can create or set your DLT acount/wallets, "Receive" button is ONLY used for receive test fund from Overledger development faucet.

![Purchase page](../docs/example_004.png)
 
Go to purchase panel by clicking items at main page, from this page, you could modify transaction details, also you can drag a file into a marked area.
Maximum accept file size is 50KB.
