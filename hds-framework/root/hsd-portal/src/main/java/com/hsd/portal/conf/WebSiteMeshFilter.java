package com.hsd.portal.conf;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/my/*", "/decorators/default.jsp").addExcludedPath("/js/*")
				.addExcludedPath("/plugin/*").addExcludedPath("/css/*");
	}

}
