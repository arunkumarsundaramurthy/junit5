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

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.engine.AbstractJupiterTestEngineTests;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.launcher.LauncherDiscoveryRequest;

/**
 * Test correct test discovery in simple test classes for {@link DisplayNameGeneration}.
 *
 * @since 5.4
 */
class DisplayNameGenerationTests extends AbstractJupiterTestEngineTests {

	@Test
	void defaultStyle() {
		check(DefaultStyle.class, //
			"CONTAINER: DisplayNameGenerationTests$DefaultStyle", //
			"TEST: test()", //
			"TEST: test(TestInfo)", //
			"TEST: testUsingCamelCaseStyle()", //
			"TEST: testUsingCamelCaseStyle_AndSomePrefix()", //
			"TEST: test_with_underscores()" //
		);
	}

	@Test
	void underscoreStyle() {
		check(UnderscoreStyle.class, //
			"CONTAINER: DisplayNameGenerationTests$UnderscoreStyle", //
			"TEST: test with underscores()", //
			"TEST: test()", //
			"TEST: test(TestInfo)", //
			"TEST: testUsingCamelCaseStyle AndSomePrefix()", //
			"TEST: testUsingCamelCaseStyle()" //
		);
	}

	private void check(Class<?> testClass, String... expectedDisplayNames) {
		LauncherDiscoveryRequest request = request().selectors(selectClass(testClass)).build();
		Set<? extends TestDescriptor> descriptors = discoverTests(request).getDescendants();
		List<String> sortedNames = descriptors.stream().map(this::describe).sorted().collect(toList());
		assertLinesMatch(Arrays.asList(expectedDisplayNames), sortedNames);
	}

	private String describe(TestDescriptor descriptor) {
		return descriptor.getType() + ": " + descriptor.getDisplayName();
	}

	// -------------------------------------------------------------------

	static abstract class AbstractTestCase {
		@Test
		void test() {
		}

		@Test
		void test(TestInfo testInfo) {
		}

		@Test
		void testUsingCamelCaseStyle() {
		}

		@Test
		void testUsingCamelCaseStyle_AndSomePrefix() {
		}

		@Test
		void test_with_underscores() {
		}
	}

	@DisplayNameGeneration(DisplayNameGeneration.Style.DEFAULT)
	static class DefaultStyle extends AbstractTestCase {
	}

	@DisplayNameGeneration(DisplayNameGeneration.Style.UNDERSCORE)
	static class UnderscoreStyle extends AbstractTestCase {
	}

}
