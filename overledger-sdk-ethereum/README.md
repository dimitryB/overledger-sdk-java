# overledger-sdk-ethereum

This module implements Account specifically for Ethereum blockchain

## network.quant.ethereum.EthereumAccount

[EthereumAccount](./src/network/quant/ethereum/EthereumAccount.java) is the Ethereum implementation of Account

Instance of EthereumAccount class by using EthereumAccount#getInstance(NETWORK) function.
If no private key and nonce provided, a new private key will be automatically generated with nonce ZERO.

Use EthereumAccount#sign(fromAddress, toAddress, message, dltTransaction) to sign DLT transaction.

# Experimental library

## network.quant.ethereum.experimental.EthereumFaucetHelper

[EthereumFaucetHelper](./src/network/quant/ethereum/experimental/EthereumFaucetHelper.java) can be used for accessing Overledger Ethereum faucet.
Call function EthereumFaucetHelper#fundAccount(EthereumAccount) to fund giving account 1 ETH (from Overledger testnet).

Application uses EthereumFaucetHelper need to have Faucet URL, this module does not provide Faucet URL.
