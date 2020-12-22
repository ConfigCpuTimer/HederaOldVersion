package com.kyousuke.hedera;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.HederaStatusException;
import com.hedera.hashgraph.sdk.TransactionId;
import com.hedera.hashgraph.sdk.account.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.account.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PublicKey;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException, HederaStatusException {

        // Grab your Hedera testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Objects.requireNonNull(Dotenv.load().get("MY_ACCOUNT_ID")));
        Ed25519PrivateKey myPrivateKey = Ed25519PrivateKey.fromString(Objects.requireNonNull(Dotenv.load().get("MY_PRIVATE_KEY")));

        // Create your Hedera testnet client
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);

        // Generate a new key pair
        Ed25519PrivateKey newAccountPrivateKey = Ed25519PrivateKey.generate();
        Ed25519PublicKey newAccountPublicKey = newAccountPrivateKey.publicKey;

        //Create new account and assign the public key
        TransactionId newAccount = new AccountCreateTransaction()
                .setKey(newAccountPublicKey)
                .setInitialBalance(1000)
                .execute(client);

        // Get the new account ID
        AccountId newAccountId = newAccount.getReceipt(client).getAccountId();

        System.out.println("The new account ID is: " +newAccountId);

        //Check the new account's balance
        /*Hbar accountBalance = new AccountBalanceQuery()
                .setAccountId(newAccountId)
                .execute(client);

        System.out.println("The new account balance is: " +accountBalance);*/
    }
}
