# overledger-sdk-ripple-java

This module implements Account specifically for Ripple blockchain

This module used source code from Ripple Java Library (https://github.com/ripple-unmaintained/ripple-lib-java).
This original Ripple Java Library is unmaintained, and no Maven repository found on Maven central.

We have to extract necessary classes for a lite version for our SDK usage.

## network.quant.ripple.RippleAccount

[RippleAccount](./src/network/quant/ripple/RippleAccount.java) is the Ripple implementation of Account

Instance of RippleAccount class by using RippleAccount#getInstance(secretKey) function.

Use RippleAccount#sign(fromAddress, toAddress, message, dltTransaction) to sign DLT transaction.

# Experimental library

## network.quant.ripple.experimental.RippleFaucetHelper

[RippleFaucetHelper](./src/network/quant/ripple/experimental/RippleFaucetHelper.java) can be used for accessing Overledger Ripple faucet.
Call function RippleFaucetHelper#fundAccount(secretKey, amount) to fund giving account amount XRP (from Overledger testnet).

Application uses RippleFaucetHelper need to have Faucet URL, this module does not provide Faucet URL.
