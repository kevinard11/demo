package com.yukiii.demo.adapter.builder;

import com.yukiii.demo.util.NumberUtil;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalBuilder {
  private BigDecimal number;

  public BigDecimalBuilder() {
    this.number = BigDecimal.ZERO;
  }

  public BigDecimalBuilder(BigDecimal number) {
    this.number = BigDecimal.ZERO;
    this.number = number;
  }

  public BigDecimalBuilder add(BigDecimal add) {
    this.number = NumberUtil.add(this.number, add);
    return this;
  }

  public BigDecimalBuilder add(double add) {
    this.number = this.add(new BigDecimal(add)).build();
    return this;
  }

  public BigDecimalBuilder substract(BigDecimal substract) {
    this.number = NumberUtil.substract(this.number, substract);
    return this;
  }

  public BigDecimalBuilder substract(double substract) {
    this.number = this.substract(new BigDecimal(substract)).build();
    return this;
  }

  public BigDecimalBuilder times(BigDecimal times) {
    this.number = NumberUtil.times(this.number, times);
    return this;
  }

  public BigDecimalBuilder times(double times) {
    this.number = this.times(new BigDecimal(times)).build();
    return this;
  }

  public BigDecimalBuilder divide(BigDecimal divisor) {
    this.number = NumberUtil.divide(this.number, divisor);
    return this;
  }

  public BigDecimalBuilder divide(double divisor) {
    this.number = this.divide(new BigDecimal(divisor)).build();
    return this;
  }

  public BigDecimalBuilder sum(List<BigDecimal> daftarNumber) {
    if (daftarNumber == null) {
      throw new NullPointerException("Argument tidak boleh null");
    } else {
      this.number = (BigDecimal)daftarNumber.stream().reduce(this.number, (identity, n) -> {
        return NumberUtil.add(identity, n);
      });
      return this;
    }
  }

  public BigDecimal build() {
    return this.number;
  }

}
