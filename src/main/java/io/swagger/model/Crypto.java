package io.swagger.model;

import javax.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Crypto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-05-15T17:26:22.532+02:00[Europe/Berlin]")


@Entity
@Table(name = "jnktkmz_crypto")
public class Crypto   {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  private Long id = null;

  @Column
  @JsonProperty("sender_address")
  private String senderAddress = null;

  @Column
  @JsonProperty("receiver_address")
  private String receiverAddress = null;

  @Column
  @JsonProperty("amount")
  private String amount = null;

  @Column
  @JsonProperty("currency")
  private String currency = null;

  @Column
  @JsonProperty("transaction_date")
  private String transactionDate = null;

  @Column
  @JsonProperty("transaction_type")
  private String transactionType = null;

  @Column
  @JsonProperty("transaction_status")
  private String transactionStatus = null;

  @Column
  @JsonProperty("transaction_fee")
  private String transactionFee = null;

  public Crypto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * id autoincrementale
   * @return id
   **/
  @Schema(example = "10", description = "id autoincrementale")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Crypto senderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
    return this;
  }

  /**
   * Get senderAddress
   * @return senderAddress
   **/
  @Schema(example = "524405897480283304730031118657307613159097243609", description = "")
  
    public String getSenderAddress() {
    return senderAddress;
  }

  public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
  }

  public Crypto receiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
    return this;
  }

  /**
   * Get receiverAddress
   * @return receiverAddress
   **/
  @Schema(example = "644179777838701048311948174801540410452640022671", description = "")
  
    public String getReceiverAddress() {
    return receiverAddress;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
  }

  public Crypto amount(String amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "447498.8", description = "")
  
    public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public Crypto currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   **/
  @Schema(example = "CAD", description = "")
  
    public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Crypto transactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

  /**
   * Get transactionDate
   * @return transactionDate
   **/
  @Schema(example = "7/29/2022", description = "")
  
    public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public Crypto transactionType(String transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
   **/
  @Schema(example = "sell", description = "")
  
    public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public Crypto transactionStatus(String transactionStatus) {
    this.transactionStatus = transactionStatus;
    return this;
  }

  /**
   * Get transactionStatus
   * @return transactionStatus
   **/
  @Schema(example = "pending", description = "")
  
    public String getTransactionStatus() {
    return transactionStatus;
  }

  public void setTransactionStatus(String transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

  public Crypto transactionFee(String transactionFee) {
    this.transactionFee = transactionFee;
    return this;
  }

  /**
   * Get transactionFee
   * @return transactionFee
   **/
  @Schema(example = "95.39", description = "")
  
    public String getTransactionFee() {
    return transactionFee;
  }

  public void setTransactionFee(String transactionFee) {
    this.transactionFee = transactionFee;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Crypto crypto = (Crypto) o;
    return Objects.equals(this.id, crypto.id) &&
        Objects.equals(this.senderAddress, crypto.senderAddress) &&
        Objects.equals(this.receiverAddress, crypto.receiverAddress) &&
        Objects.equals(this.amount, crypto.amount) &&
        Objects.equals(this.currency, crypto.currency) &&
        Objects.equals(this.transactionDate, crypto.transactionDate) &&
        Objects.equals(this.transactionType, crypto.transactionType) &&
        Objects.equals(this.transactionStatus, crypto.transactionStatus) &&
        Objects.equals(this.transactionFee, crypto.transactionFee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, senderAddress, receiverAddress, amount, currency, transactionDate, transactionType, transactionStatus, transactionFee);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Crypto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    senderAddress: ").append(toIndentedString(senderAddress)).append("\n");
    sb.append("    receiverAddress: ").append(toIndentedString(receiverAddress)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    transactionDate: ").append(toIndentedString(transactionDate)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    transactionStatus: ").append(toIndentedString(transactionStatus)).append("\n");
    sb.append("    transactionFee: ").append(toIndentedString(transactionFee)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
