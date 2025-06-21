package com.blo.sales.utils;

public interface IToOuter<I, O> {

	O toOuter(I inner);
}
