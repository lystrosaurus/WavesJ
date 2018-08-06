package com.wavesplatform.wavesj.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wavesplatform.wavesj.*;

import java.nio.ByteBuffer;

import static com.wavesplatform.wavesj.ByteUtils.*;

public class ReissueTransactionV1 extends TransactionWithSignature implements ReissueTransaction {

    private PublicKeyAccount senderPublicKey;
    private byte chainId;
    private String assetId;
    private long quantity;
    private boolean reissuable;
    private long fee;
    private long timestamp;

    @JsonCreator
    public ReissueTransactionV1(@JsonProperty("senderPublicKey") PublicKeyAccount senderPublicKey,
                                @JsonProperty("chainId") byte chainId,
                                @JsonProperty("assetId") String assetId,
                                @JsonProperty("quantity") long quantity,
                                @JsonProperty("reissuable") boolean reissuable,
                                @JsonProperty("fee") long fee,
                                @JsonProperty("timestamp") long timestamp,
                                @JsonProperty("signature") ByteString signature) {
        super(signature);
        this.senderPublicKey = senderPublicKey;
        this.chainId = chainId;
        this.assetId = assetId;
        this.quantity = quantity;
        this.reissuable = reissuable;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    public ReissueTransactionV1(PrivateKeyAccount senderPublicKey,
                                byte chainId,
                                String assetId,
                                long quantity,
                                boolean reissuable,
                                long fee,
                                long timestamp) {
        super(senderPublicKey);
        this.senderPublicKey = senderPublicKey;
        this.chainId = chainId;
        this.assetId = assetId;
        this.quantity = quantity;
        this.reissuable = reissuable;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    public PublicKeyAccount getSenderPublicKey() {
        return senderPublicKey;
    }

    public byte getChainId() {
        return chainId;
    }

    public String getAssetId() {
        return assetId;
    }

    public long getQuantity() {
        return quantity;
    }

    public boolean isReissuable() {
        return reissuable;
    }

    public long getFee() {
        return fee;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public byte[] getBytes() {
        ByteBuffer buf = ByteBuffer.allocate(KBYTE);
        buf.put(ReissueTransaction.REISSUE)
                .put(senderPublicKey.getPublicKey()).put(Base58.decode(assetId)).putLong(quantity)
                .put((byte) (reissuable ? 1 : 0))
                .putLong(fee).putLong(timestamp);
        return ByteArraysUtils.getOnlyUsed(buf);
    }

    @Override
    public byte getType() {
        return REISSUE;
    }

    @Override
    public byte getVersion() {
        return Transaction.V1;
    }
}
