/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.api;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.lang.reflect.Method;

import org.apiguardian.api.API;

/**
 * {@code DisplayNameGenerator} defines the SPI for
 * generating display names programmatically.
 *
 * @since 5.4
 * @see DisplayName
 * @see DisplayNameGeneration
 */
@API(status = EXPERIMENTAL, since = "5.4")
public interface DisplayNameGenerator {

	/**
	 * TODO Javadoc
	 */
	String generateDisplayNameForClass(Class<?> testClass);

	/**
	 * TODO Javadoc
	 * TODO Find better name, split into two different methods?
	 */
	String generateDisplayNameForNestedClass(Class<?> nestedClass);

	/**
	 * TODO Javadoc
	 */
	String generateDisplayNameForMethod(Class<?> testClass, Method testMethod);
}
