package ru.otus.core.sessionmanager;

import java.util.function.Supplier;

public interface TransactionAction<T> extends Supplier<T> {}
