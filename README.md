[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Overledger Java SDK

Developer's guide to use the Overledger SDK written in Java by Quant Network.

## Introduction to the Overledger SDK

Overledger is an operating system that allows distributed apps (MApps) to connect to multiple distributed ledger technologies (DLTs) or blockchains. The Overledger SDK allows developers to create signed transactions & send them simultaneously to all supported DLTs.

## Technologies

The Overledger SDK is maven compatible dependency written in _TODO:_ [state what particular flavour was used]

### Overledger SDK Structure

![Project Layer](./docs/sdk_layer.png)

### Overledger SDK Working Flow

![Project Flow](./docs/sdk_flow.png)

## Prerequisites

- Register for a free developer account on [Quant Developer's Portal](https://developer.quant.network)
- You will require MAppId and BPI key:
  - Enter information regarding your application in order to get a MApp ID.
  - Verfify your Quant token, and create a BPI key.

## Installation

Developers would have to install the Overledger SDK as a maven dependency.

_TODO:_ Link to mvn repository here

Example dependency:

```xml
<!-- SDK bundle -->
<dependency>
    <groupId>io.overledger</groupId>
    <artifactId>overledger-sdk-bundle</artifactId>
    <version>1.0.0-alpha</version>
</dependency>
```

### Tailored installation

When a full implementation of all dependencies is not required, it can be tailored to only implement those services that will be utilised.

#### [overledger-sdk-api](./overledger-sdk-api/README.md)

API module defines Overledger SDK interfaces.

#### [overledger-sdk-essential](./overledger-sdk-essential/README.md)

This module gives a basic implementation of Overledger SDK API.

#### [overledger-sdk-bitcoin](./overledger-sdk-bitcoin/README.md)

This module contains Bitcoin implementation of Overledger Account API.

#### [overledger-sdk-ethereum](./overledger-sdk-ethereum/README.md)

This module contains Ethereum implementation of Overledger Account API.

#### [overledger-sdk-ripple](./overledger-sdk-ripple/README.md)

This module contains Ripple implementation of Overledger Account API.

## Getting started

_TODO:_ Describe how to get started, what initial configuration will be required and how to initialise it.

### Further information

This SDK acts as library for embedding in an application, and facilitates the execution and access of Quant Network's Overledger.

Refer [wiki](https://github.com/quantnetwork/overledger-sdk-java/wiki) for more Details.

| Stable Release Version | JDK Version compatibility | BPI Version compatibility | Release Date |
| ---------------------- | ------------------------- | ------------------------- | ------------ |
| 1.0.0-alpha            | 1.8+                      | 1.0.0-alpha               | \*30/10/2018 |

## Release notes

| Release       |                        Notes                                     |
| ------------- | :--------------------------------------------------------------: |
| 1.0.0-alpha   | [v1.0.0-alpha release notes](docs/release_v1.0.0-alpha_notes.md) |
