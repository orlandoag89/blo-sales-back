package com.blo.sales.utils;

public interface IToInner<I, O> {

	I toInner(O outer);
}
