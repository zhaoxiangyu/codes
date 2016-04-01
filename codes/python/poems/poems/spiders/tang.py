# -*- coding: utf-8 -*-
import scrapy


class TangSpider(scrapy.Spider):
    name = "tang"
    allowed_domains = ["http://www.gushiwen.org/gushi/tangshi.aspx?WebShieldDRSessionVerify=TyMCwWrB7ckiq3Rhuo33"]
    start_urls = (
        'http://www.http://www.gushiwen.org/gushi/tangshi.aspx?WebShieldDRSessionVerify=TyMCwWrB7ckiq3Rhuo33/',
    )

    def parse(self, response):
        pass
