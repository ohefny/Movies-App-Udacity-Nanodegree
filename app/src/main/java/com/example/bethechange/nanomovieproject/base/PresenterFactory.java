package com.example.bethechange.nanomovieproject.base;

/**
 * Creates a Presenter object.
 * @param <T> presenter type
 */
public interface PresenterFactory<T extends BasePresenter> {
    T create();
}
