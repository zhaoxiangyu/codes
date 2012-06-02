<?xml version="1.0" encoding="UTF-8" ?>

<!-- New document created with EditiX at Tue Nov 22 14:59:22 CST 2011 -->

<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:xdt="http://www.w3.org/2005/xpath-datatypes"
	xmlns:err="http://www.w3.org/2005/xqt-errors"
	exclude-result-prefixes="xs xdt err fn">

	<xsl:output method="xml" indent="yes"/>
	<xsl:param name="libproject-list"/>
	<xsl:variable name="list-xml" select="document($libproject-list)"/>
	
	<!-- <xsl:template match="/">
		<xsl:apply-templates select="manifest"/>
	</xsl:template> -->

	<xsl:template match="manifest">
		<!-- <echo><xsl:value-of select="concat($libproject-list,'/AndroidManifest.xml')"/></echo> -->
		<!-- <echo><xsl:value-of select="tokenize('a b c d e',' ')[1]"/></echo> -->
		<!-- <echo><xsl:value-of select="tokenize($lib-projects,',')[1]"/></echo> -->
		<!-- <echo><xsl:value-of select="$libproject-list"/></echo> -->
		<!-- <xsl:element name="{name()}"/> -->
		<xsl:copy select=".">
			<xsl:copy-of select="*"/>
			<xsl:for-each select="$list-xml//lib-project">
				<xsl:variable name="lib-xml-path" select="concat('../../',.,'/AndroidManifest.xml')"/>
				<xsl:variable name="lib-xml" select="document($lib-xml-path)"/>
				<xsl:copy-of select="$lib-xml//uses-permission"/>
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
