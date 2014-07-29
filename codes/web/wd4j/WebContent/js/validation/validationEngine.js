(function($) {
	$.fn.validationEngine = function(settings) {
		if ($.validationEngineLanguage) {
			allRules = $.validationEngineLanguage.allRules
		} else {
			$.validationEngine.debug("验证脚本没加载完全，请检查脚本")
		}
		settings = jQuery.extend({
			allrules : allRules,
			validationEventTriggers : "focusout",
			inlineValidation : true,
			returnIsValid : false,
			liveEvent : true,
			unbindEngine : true,
			ajaxSubmit : false,
			scroll : true,
			promptPosition : "bottomRight",
			success : true,
			beforeSuccess : function() {
			},
			failure : function() {
			},
			showArray : false,
			showOnMouseOver : true,
			errorClass : "error-field"
		}, settings);
		$.validationEngine.settings = settings;
		if(!$.validationEngine.ajaxValidArray||$.validationEngine.ajaxValidArray==""){
		$.validationEngine.ajaxValidArray = new Array(); 
		}
		var yzId = 1;
		if (settings.inlineValidation == true) {
			if (!settings.returnIsValid) {
				allowReturnIsvalid = false;
				if (settings.liveEvent) {
					$(this).find("[class*=validate][type!=checkbox][type!=radio]").live(settings.validationEventTriggers, function(caller) {
						_inlinEvent(this)
					});
					$(this).find("[class*=validate][type=checkbox]").live("click", function(caller) {
						_inlinEvent(this)
					});
					$(this).find("[class*=validate][type=radio]").live("click", function(caller) {
						_inlinEvent(this)
					});
					$(this).find("[class*=validate][type=file]").live("click", function(caller) {
						_inlinEvent(this)
					});
					$(this).find("select[class*=validate]").live("focus", function(caller) {
						_inlinEvent(this)
					});
//					$(this).find("input:text[class*=date]").live("blur", function(caller) {alert(5);
//						_inlinEvent(this)
//					})
				} else {
					$(this).find("[class*=validate]").not("[type=checkbox]").not("[type=radio]").bind(settings.validationEventTriggers, function(caller) {
						_inlinEvent(this)
					});
					$(this).find("[class*=validate][type=checkbox]").bind("click", function(caller) {
						_inlinEvent(this)
					});
					$(this).find("[class*=validate][type=radio]").bind("click", function(caller) {
						_inlinEvent(this)
					});
					$(this).find("[class*=validate][type=file]").bind("click", function(caller) {
						_inlinEvent(this)
					});
					$(this).find("select[class*=validate]").bind("focus", function(caller) {
						_inlinEvent(this)
					});
//					$(this).find("input:text[class*=date]").bind("blur", function(caller) {
//						_inlinEvent(this)
//					})
				}
				//给表单中所有元素附加yzId
				firstvalid = false;
				$(this).find("[type!=button]").each(function() {
					$(this).attr("yzId", yzId);
					yzId++
				})
			}
			function _inlinEvent(caller) {
				$.validationEngine.settings = settings;
				if ($.validationEngine.intercept == false || !$.validationEngine.intercept) {
					$.validationEngine.onSubmitValid = false;
					$.validationEngine.loadValidation(caller)
				} else {
					$.validationEngine.intercept = false
				}
			}

		}
		if (settings.returnIsValid) {
			if ($.validationEngine.submitValidation(this, settings)) {
				return false
			} else {
				return true
			}
		}
		$(this).bind("submit", function(caller) {
			$.validationEngine.onSubmitValid = true;
			$.validationEngine.settings = settings;
			if ($.validationEngine.submitValidation(this, settings) == false) {
				if ($.validationEngine.submitForm(this, settings) == true) {
					return false
				}
			} else {
				settings.failure && settings.failure();
				return false
			}
		});
		$(".formError").live("click", function() {
			$(this).fadeOut(150, function() {
				$(this).remove()
			})
		})
	};
	$.validationEngine = {
		defaultSetting : function(caller) {
			if ($.validationEngineLanguage) {
				allRules = $.validationEngineLanguage.allRules
			} else {
				$.validationEngine.debug("验证脚本没加载完全，请检查脚本")
			}
			settings = {
				allrules : allRules,
				validationEventTriggers : "blur",
				inlineValidation : true,
				returnIsValid : false,
				scroll : true,
				unbindEngine : true,
				ajaxSubmit : false,
				promptPosition : "bottomRight",
				success : false,
				failure : function() {
				}
			};
			$.validationEngine.settings = settings
		},
		loadValidation : function(caller) {
			if (!$.validationEngine.settings) {
				$.validationEngine.defaultSetting()
			}
			rulesParsing = $(caller).attr("class");
			rulesRegExp = /\[(.*)\]/;
			getRules = rulesRegExp.exec(rulesParsing);
			str = getRules[1];
			pattern = /\[|,|\]/;
			result = str.split(pattern);
			var validateCalll = $.validationEngine.validateCall(caller, result);
			return validateCalll
		},
		validateCall : function(caller, rules) {
			var promptText = "";
			caller = caller;
			ajaxValidate = false;
			var callerName = $(caller).attr("name");
			$.validationEngine.isError = false;
			$.validationEngine.showTriangle = $.validationEngine.settings.showArray;
			callerType = $(caller).attr("type");
			for ( i = 0; i < rules.length; i++) {
				switch(rules[i]) {
					case"optional":
						if (!$(caller).val()) {
							$.validationEngine.closePrompt(caller);
							return $.validationEngine.isError
						}
						break;
					case"required":
						_required(caller, rules);
						break;
					case"custom":
						_customRegex(caller, rules, i);
						break;
					case"exemptString":
						_exemptString(caller, rules, i);
						break;
					case"ajax":
						if (!$.validationEngine.onSubmitValid) {
							_ajax(caller, rules, i)
						}
						break;
					case"length":
						_length(caller, rules, i);
						break;
					case"maxCheckbox":
						_maxCheckbox(caller, rules, i);
						groupname = $(caller).attr("name");
						caller = $("input[name='" + groupname + "']");
						break;
					case"minCheckbox":
						_minCheckbox(caller, rules, i);
						groupname = $(caller).attr("name");
						caller = $("input[name='" + groupname + "']");
						break;
					case"confirm":
						_confirm(caller, rules, i);
						break;
					case"funcCall":
						_funcCall(caller, rules, i);
						break;
					case"isIdCardNo":
						_isIdCardNo(caller, rules);
						break;
					case"isCarNo":
						_customRegex(caller, rules, i-1);
						break;
					default:
				}
			}
			radioHack();
			if ($.validationEngine.isError == true) {
				linkTofield = $.validationEngine.linkTofield(caller);
				($("div." + linkTofield).size() == 0) ? $.validationEngine.buildPrompt(caller, promptText, "error") : $.validationEngine.updatePromptText(caller, promptText)
			} else {
				$.validationEngine.closePrompt(caller)
			}
			function radioHack() {
				if ($("input[name='" + callerName + "']").size() > 1 && (callerType == "radio" || callerType == "checkbox")) {
					caller = $("input[name='" + callerName + "'][type!=hidden]:first");
					$.validationEngine.showTriangle = false
				}
			}

			function _required(caller, rules) {
				callerType = $(caller).attr("type");
				if (callerType == "text" || callerType == "password" || callerType == "textarea"|| callerType == "file") {
					if (!$(caller).val()||$(caller).val()=='请选择') {
						$.validationEngine.isError = true;
						promptText += $.validationEngine.settings.allrules[rules[i]].alertText + "<br />"
					}
				}
				if (callerType == "radio" || callerType == "checkbox") {
					callerName = $(caller).attr("name");
					if ($("input[name='" + callerName + "']:checked").size() == 0) {
						$.validationEngine.isError = true;
						if ($("input[name='" + callerName + "']").size() == 1) {
							promptText += $.validationEngine.settings.allrules[rules[i]].alertTextCheckboxe + "<br />"
						} else {
							promptText += $.validationEngine.settings.allrules[rules[i]].alertTextCheckboxMultiple + "<br />"
						}
					}
				}
				if (callerType == "select-one") {
					if (!$(caller).val()) {
						$.validationEngine.isError = true;
						promptText += $.validationEngine.settings.allrules[rules[i]].alertText + "<br />"
					}
				}
				if (callerType == "select-multiple") {
					if (!$(caller).find("option:selected").val()) {
						$.validationEngine.isError = true;
						promptText += $.validationEngine.settings.allrules[rules[i]].alertText + "<br />"
					}
				}
			}
			
			function _isIdCardNo(caller, rules) {
				callerType = $(caller).attr("type");
				if (!isIdCardNo($(caller).val())) {
					$.validationEngine.isError = true;
					promptText += $.validationEngine.settings.allrules[rules[i]].alertText + "<br />"
				}
			}
			
			function isIdCardNo(idCard){
    
				var id=idCard;
			    var id_length=id.length;
			
			    if (id_length==0 || idCard=="无"){
			        return true;
			    }
			    if (id_length!=15 && id_length!=18){
			        return false;
			    }
			
			    if (id_length==15){
			        yyyy="19"+id.substring(6,8);
			        mm=id.substring(8,10);
			        dd=id.substring(10,12);
			
			        if (mm>12 || mm<=0){
			            return false;
			        }
			
			        if (dd>31 || dd<=0){
			            return false;
			        }
			
					/*
			        birthday=yyyy+ "-" +mm+ "-" +dd;
			
			        if ("13579".indexOf(id.substring(14,15))!=-1){
			            sex="1";
			        }else{
			            sex="2";
			        }
			        */
			    }else if (id_length==18){
			        if (id.indexOf("X") > 0 && id.indexOf("X")!=17 || id.indexOf("x")>0 && id.indexOf("x")!=17){
			            return false;
			        }
			        yyyy=id.substring(6,10);
			        if (yyyy>2200 || yyyy<1900){
			            return false;
			        }
			
			        mm=id.substring(10,12);
			        if (mm>12 || mm<=0){
			            return false;
			        }
			
			        dd=id.substring(12,14);
			        if (dd>31 || dd<=0){
			            return false;
			        }
					
			        if (id.charAt(17)=="x" || id.charAt(17)=="X")
			        {
			            if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id)){
			                return false;
			            }
			
			        }else{
			            if (id.charAt(17)!=GetVerifyBit(id)){
			                return false;
			            }
			        }
			        /*
			        birthday=id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14);
			        if ("13579".indexOf(id.substring(16,17)) > -1){
			            sex="1";
			        }else{
			            sex="2";
			        }
			        */
			    }
			
			    return true;
			}
			
			//15位转18位中,计算校验位即最后一位
			
			
			function GetVerifyBit(id){
			    var result;
			    var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
			    nNum=nNum%11;
			    switch (nNum) {
			       case 0 :
			          result="1";
			          break;
			       case 1 :
			          result="0";
			          break;
			       case 2 :
			          result="X";
			          break;
			       case 3 :
			          result="9";
			          break;
			       case 4 :
			          result="8";
			          break;
			       case 5 :
			          result="7";
			          break;
			       case 6 :
			          result="6";
			          break;
			       case 7 :
			          result="5";
			          break;
			       case 8 :
			          result="4";
			          break;
			       case 9 :
			          result="3";
			          break;
			       case 10 :
			          result="2";
			          break;
			    }
			    //document.write(result);
			    return result;
			}
						
			function _customRegex(caller, rules, position) {
				customRule = rules[position + 1];
				pattern = eval($.validationEngine.settings.allrules[customRule].regex);
				if (!pattern.test($(caller).attr("value"))) {
					$.validationEngine.isError = true;
					promptText += $.validationEngine.settings.allrules[customRule].alertText + "<br />"
				}
			}

			function _exemptString(caller, rules, position) {
				customString = rules[position + 1];
				if (customString == $(caller).attr("value")) {
					$.validationEngine.isError = true;
					promptText += $.validationEngine.settings.allrules.required.alertText + "<br />"
				}
			}

			function _funcCall(caller, rules, position) {
				customRule = rules[position + 1];
				funce = $.validationEngine.settings.allrules[customRule].nname;
				var fn = window[funce];
				if ( typeof (fn) === "function") {
					var fn_result = fn();
					$.validationEngine.isError = fn_result;
					promptText += $.validationEngine.settings.allrules[customRule].alertText + "<br />"
				}
			}

			function _ajax(caller, rules, position) {
				customAjaxRule = rules[position + 1];
				postfile = $.validationEngine.settings.allrules[customAjaxRule].file;
				fieldValue = $(caller).val();
				ajaxCaller = caller;
				fieldId = $(caller).attr("yzId");
				ajaxValidate = true;
				ajaxisError = $.validationEngine.isError;
				var level= caller.level;
				if ($.validationEngine.settings.allrules[customAjaxRule].extraData) {
					extraData = $.validationEngine.settings.allrules[customAjaxRule].extraData
				} else {
					extraData = ""
				}
				if (!ajaxisError) {
					$.ajax({
						type : "POST",
						url : postfile,
						async : false,
						data : "validateValue=" + encodeURI(fieldValue) + "&validateId=" + fieldId + "&validateError=" + customAjaxRule +"&level="+level ,
						beforeSend : function() {
							if ($.validationEngine.settings.allrules[customAjaxRule].alertTextLoad) {
								if (!$("div."+fieldId+"formError")[0]) {
									return $.validationEngine.buildPrompt(ajaxCaller, $.validationEngine.settings.allrules[customAjaxRule].alertTextLoad, "load")
								} else {
									$.validationEngine.updatePromptText(ajaxCaller, $.validationEngine.settings.allrules[customAjaxRule].alertTextLoad, "load")
									
								}
							}
						},
						error : function(data, transport) {
							$.validationEngine.debug("ajax出错: " + data.status + " " + transport)
						},
						success : function(data) {
							data = eval("(" + data + ")");
							ajaxisError = data[0];
//							ajaxCaller = $("#"+data[0])[0];
							fieldId = ajaxCaller;
							ajaxErrorLength = $.validationEngine.ajaxValidArray.length;
							existInarray = false;
							if (ajaxisError == "false") {
								_checkInArray(false);
								if (!existInarray) {
									$.validationEngine.ajaxValidArray[ajaxErrorLength] = new Array(2);
									$.validationEngine.ajaxValidArray[ajaxErrorLength][0] = fieldId;
									$.validationEngine.ajaxValidArray[ajaxErrorLength][1] = false;
									existInarray = false
								}
								$.validationEngine.ajaxValid = false;
								promptText += $.validationEngine.settings.allrules[customAjaxRule].alertText + "<br />";
								//$.validationEngine.updatePromptText(ajaxCaller, promptText, "", true)
								$.validationEngine.buildPrompt(ajaxCaller, promptText, "load");
							} else {
								_checkInArray(true);
								$.validationEngine.ajaxValid = true;
								if (!customAjaxRule) {
									$.validationEngine.debug("wrong ajax response, are you on a server or in xampp? if not delete de ajax[ajaxUser] validating rule from your form ")
								}
								if ($.validationEngine.settings.allrules[customAjaxRule].alertTextOk) {
									//$.validationEngine.updatePromptText(ajaxCaller, $.validationEngine.settings.allrules[customAjaxRule].alertTextOk, "pass", true)
									$.validationEngine.buildPrompt(ajaxCaller, $.validationEngine.settings.allrules[customAjaxRule].alertTextOk, "load");
								} else {
									ajaxValidate = false;
									$.validationEngine.closePrompt(ajaxCaller)
								}
							}
							function _checkInArray(validate) {
								for ( x = 0; x < ajaxErrorLength; x++) {
									if ($.validationEngine.ajaxValidArray[x][0] == fieldId) {
										$.validationEngine.ajaxValidArray[x][1] = validate;
										existInarray = true
									}
								}
							}

						}
					})
				}
			}

			function _confirm(caller, rules, position) {
				confirmField = rules[position + 1];
				if ($(caller).attr("value") != $("#" + confirmField).attr("value")) {
					$.validationEngine.isError = true;
					promptText += $.validationEngine.settings.allrules.confirm.alertText + "<br />"
				}
			}

			function _length(caller, rules, position) {
				startLength = eval(rules[position + 1]);
				endLength = eval(rules[position + 2]);
				feildLength = $(caller).attr("value").length;
				if (feildLength < startLength || feildLength > endLength) {
					$.validationEngine.isError = true;
					promptText += $.validationEngine.settings.allrules.length.alertText + startLength + $.validationEngine.settings.allrules.length.alertText2 + endLength + $.validationEngine.settings.allrules.length.alertText3 + "<br />"
				}
			}

			function _maxCheckbox(caller, rules, position) {
				nbCheck = eval(rules[position + 1]);
				groupname = $(caller).attr("name");
				groupSize = $("input[name='" + groupname + "']:checked").size();
				if (groupSize > nbCheck) {
					$.validationEngine.showTriangle = false;
					$.validationEngine.isError = true;
					promptText += $.validationEngine.settings.allrules.maxCheckbox.alertText + "<br />"
				}
			}

			function _minCheckbox(caller, rules, position) {
				nbCheck = eval(rules[position + 1]);
				groupname = $(caller).attr("name");
				groupSize = $("input[name='" + groupname + "']:checked").size();
				if (groupSize < nbCheck) {
					$.validationEngine.isError = true;
					$.validationEngine.showTriangle = false;
					promptText += $.validationEngine.settings.allrules.minCheckbox.alertText + " " + nbCheck + " " + $.validationEngine.settings.allrules.minCheckbox.alertText2 + "<br />"
				}
			}
			return ($.validationEngine.isError) ? $.validationEngine.isError : false
		},
		submitForm : function(caller) {
			if ($.validationEngine.settings.ajaxSubmit) {
				if ($.validationEngine.settings.ajaxSubmitExtraData) {
					extraData = $.validationEngine.settings.ajaxSubmitExtraData
				} else {
					extraData = ""
				}
				$.ajax({
					type : "POST",
					url : $.validationEngine.settings.ajaxSubmitFile,
					async : true,
					data : $(caller).serialize() + "&" + extraData,
					error : function(data, transport) {
						$.validationEngine.debug("ajax出错: " + data.status + " " + transport)
					},
					success : function(data) {
						if (data == "true") {
							$(caller).css("opacity", 1);
							$(caller).animate({
								opacity : 0,
								height : 0
							}, function() {
								$(caller).css("display", "none");
								$(caller).before("<div class='ajaxSubmit'>" + $.validationEngine.settings.ajaxSubmitMessage + "</div>");
								$.validationEngine.closePrompt(".formError", true);
								$(".ajaxSubmit").show("slow");
								if ($.validationEngine.settings.success) {
									//$.validationEngine.settings.success && $.validationEngine.settings.success();
									return false
								}
							})
						} else {
							data = eval("(" + data + ")");
							if (!data.jsonValidateReturn) {
								$.validationEngine.debug("you are not going into the success fonction and jsonValidateReturn return nothing")
							}
							errorNumber = data.jsonValidateReturn.length;
							for ( index = 0; index < errorNumber; index++) {
								fieldId = data.jsonValidateReturn[index][0];
								promptError = data.jsonValidateReturn[index][1];
								type = data.jsonValidateReturn[index][2];
								$.validationEngine.buildPrompt(fieldId, promptError, type)
							}
						}
					}
				});
				return true
			}
			if (!$.validationEngine.settings.beforeSuccess()) {
				if ($.validationEngine.settings.success) {
					if ($.validationEngine.settings.unbindEngine) {
						$(caller).unbind("submit")
					}
					//$.validationEngine.settings.success && $.validationEngine.settings.success();
					return true
				}
			} else {
				return true
			}
			return false
		},
		showTip : function(event) {
			event.data.stop();
			event.data.fadeTo(100, 1);
			event.data.css({
				top : event.pageY + 10,
				left : event.pageX - 20
			})
		},
		hideTip : function(event) {
			event.data.stop();
			event.data.fadeTo(100, 0, function() {
				$(this).hide()
			})
		},
		buildPrompt : function(caller, promptText, type, ajaxed) {
			if (!$.validationEngine.settings) {
				$.validationEngine.defaultSetting()
			}
			deleteItself = "." + $(caller).attr("yzId") + "formError";
			if ($(deleteItself)[0]) {
				$(deleteItself).get(0).validateField.unbind("mouseover", $.validationEngine.showTip).unbind("mouseout", $.validationEngine.hideTip);
				$(deleteItself).stop();
				$(deleteItself).remove()
			}
			var divFormError = document.createElement("div");
			var formErrorContent = document.createElement("div");
			var formErrorBottom = document.createElement("div");
			linkTofield = $.validationEngine.linkTofield(caller);
			$(divFormError).addClass("formError");
			if (type == "pass") {
				$(divFormError).addClass("greenPopup")
			}
			if (type == "load") {
				$(divFormError).addClass("blackPopup")
			}
			if (ajaxed) {
				$(divFormError).addClass("ajaxed")
			}
			$(divFormError).addClass(linkTofield);
			$(formErrorContent).addClass("formErrorContent");
			$(formErrorBottom).addClass("formErrorBottom");
			$("body").append(divFormError);
			$(divFormError).append(formErrorContent);
			$(divFormError).append(formErrorBottom);
			if ($.validationEngine.showTriangle != false) {
				var arrow = document.createElement("div");
				$(arrow).addClass("formErrorArrow");
				$(divFormError).append(arrow);
				if ($.validationEngine.settings.promptPosition == "bottomLeft" || $.validationEngine.settings.promptPosition == "bottomRight") {
					$(arrow).addClass("formErrorArrowBottom");
					$(arrow).html('<div class="line1"><!-- --></div><div class="line2"><!-- --></div><div class="line3"><!-- --></div><div class="line4"><!-- --></div><div class="line5"><!-- --></div><div class="line6"><!-- --></div><div class="line7"><!-- --></div><div class="line8"><!-- --></div><div class="line9"><!-- --></div><div class="line10"><!-- --></div>')
				}
				if ($.validationEngine.settings.promptPosition == "topLeft" || $.validationEngine.settings.promptPosition == "topRight") {
					$(divFormError).append(arrow);
					$(arrow).html('<div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div>')
				}
			}
			$(formErrorContent).html(promptText);
			callerTopPosition = $(caller).offset().top;
			callerleftPosition = $(caller).offset().left;
			callerWidth = $(caller).width();
			inputHeight = $(divFormError).height();
			if ($.validationEngine.settings.promptPosition == "topRight") {
				callerleftPosition += callerWidth - 30;
				callerTopPosition += -inputHeight - 10
			}
			if ($.validationEngine.settings.promptPosition == "topLeft") {
				callerTopPosition += -inputHeight - 10
			}
			if ($.validationEngine.settings.promptPosition == "centerRight") {
				callerleftPosition += callerWidth + 13
			}
			if ($.validationEngine.settings.promptPosition == "bottomLeft") {
				callerHeight = $(caller).height();
				callerleftPosition = callerleftPosition;
				callerTopPosition = callerTopPosition + callerHeight + 15
			}
			if ($.validationEngine.settings.promptPosition == "bottomRight") {
				callerHeight = $(caller).height();
				callerleftPosition += callerWidth - 30;
				callerTopPosition += callerHeight + 15
			}
			$(divFormError).css({
				opacity : $.validationEngine.settings.showOnMouseOver ? 1 : 0,
				display : $.validationEngine.settings.showOnMouseOver ? "none" : ""
			});
			if ($.validationEngine.settings.showOnMouseOver) {
				if ($(caller).is(":checkbox,:radio")) {
					$(divFormError).get(0).validateField = $(caller).parent()
				} else {
					if ($(caller).is("select")) {
						if ($(caller).parent().find("input:text").length > 0) {
							$(divFormError).get(0).validateField = $(caller).parent().find("input:text")
						} else {
							$(divFormError).get(0).validateField = $(caller).parent()
						}
					} else {
						$(divFormError).get(0).validateField = $(caller)
					}
				}
				$(divFormError).get(0).validateField.addClass($.validationEngine.settings.errorClass);
				$(divFormError).get(0).validateField.bind("mouseover", $(divFormError), $.validationEngine.showTip).bind("mouseout", $(divFormError), $.validationEngine.hideTip)
			} else {
				return $(divFormError).animate({
					opacity : 0.87
				}, function() {
					return true
				})
			}
		},
		updatePromptText : function(caller, promptText, type, ajaxed) {
			linkTofield = $.validationEngine.linkTofield(caller);
			var updateThisPrompt = "." + linkTofield;
			if (type == "pass") {
				$(updateThisPrompt).addClass("greenPopup")
			} else {
				$(updateThisPrompt).removeClass("greenPopup")
			}
			if (type == "load") {
				$(updateThisPrompt).addClass("blackPopup")
			} else {
				$(updateThisPrompt).removeClass("blackPopup")
			}
			if (ajaxed) {
				$(updateThisPrompt).addClass("ajaxed")
			} else {
				$(updateThisPrompt).removeClass("ajaxed")
			}
			$(updateThisPrompt).find(".formErrorContent").html(promptText);
			callerTopPosition = $(caller).offset().top;
			inputHeight = $(updateThisPrompt).height();
			if ($.validationEngine.settings.promptPosition == "bottomLeft" || $.validationEngine.settings.promptPosition == "bottomRight") {
				callerHeight = $(caller).height();
				callerTopPosition = callerTopPosition + callerHeight + 15
			}
			if ($.validationEngine.settings.promptPosition == "centerRight") {
				callerleftPosition += callerWidth + 13
			}
			if ($.validationEngine.settings.promptPosition == "topLeft" || $.validationEngine.settings.promptPosition == "topRight") {
				callerTopPosition = callerTopPosition - inputHeight - 10
			}
			$(updateThisPrompt).animate({
				top : callerTopPosition
			})
		},
		linkTofield : function(caller) {
			linkTofield = $(caller).attr("yzId") + "formError";
			linkTofield = linkTofield.replace(/\[/g, "");
			linkTofield = linkTofield.replace(/\]/g, "");
			return linkTofield
		},
		closePrompt : function(caller, outside) {
			if (!$.validationEngine.settings) {
				$.validationEngine.defaultSetting()
			}
			if (outside) {
				if ($.validationEngine.settings.showOnMouseOver) {
					$(caller).get(0).validateField.removeClass($.validationEngine.settings.errorClass).unbind("mouseover", $.validationEngine.showTip).unbind("mouseout", $.validationEngine.hideTip);
					$(caller).remove()
				} else {
					$(caller).fadeTo("fast", 0, function() {
						$(caller).remove()
					})
				}
				return false
			}
			if ( typeof (ajaxValidate) == "undefined") {
				ajaxValidate = false
			}
			if (!ajaxValidate) {
				linkTofield = $.validationEngine.linkTofield(caller);
				closingPrompt = "." + linkTofield;
				if ($.validationEngine.settings.showOnMouseOver) {
					if ($(closingPrompt).get(0)) {
						$(closingPrompt).get(0).validateField.removeClass($.validationEngine.settings.errorClass).unbind("mouseover", $.validationEngine.showTip).unbind("mouseout", $.validationEngine.hideTip)
					}
					$(closingPrompt).remove()
				} else {
					$(closingPrompt).fadeTo("fast", 0, function() {
						$(closingPrompt).remove()
					})
				}
			}
		},
		debug : function(error) {
			if (!$("#debugMode")[0]) {
				$("body").append("<div id='debugMode'><div class='debugError'><strong>这是调试模式，来帮你解决设置的问题。</strong></div></div>")
			}
			$(".debugError").append("<div class='debugerror'>" + error + "</div>")
		},
		submitValidation : function(caller) {
			var stopForm = false;
			$.validationEngine.ajaxValid = true;
			$(caller).find(".formError").remove();
			var toValidateSize = $(caller).find("[class*=validate]").size();
			$(caller).find("[class*=validate]").each(function() {
				linkTofield = $.validationEngine.linkTofield(this);
				if (!$("." + linkTofield).hasClass("ajaxed")) {
					var validationPass = $.validationEngine.loadValidation(this);
					return (validationPass) ? stopForm = true : ""
				}
			});
			ajaxErrorLength = $.validationEngine.ajaxValidArray.length;
			for ( x = 0; x < ajaxErrorLength; x++) {
				if ($.validationEngine.ajaxValidArray[x][1] == false) {
					$.validationEngine.ajaxValid = false
				}
			}
			if (stopForm || !$.validationEngine.ajaxValid) {
				if ($.validationEngine.settings.scroll) {
					destination = $(".formError:not('.greenPopup'):first").offset().top;
					$(".formError:not('.greenPopup')").each(function() {
						testDestination = $(this).offset().top;
						if (destination > testDestination) {
							destination = $(this).offset().top
						}
					});
					$("html:not(:animated),body:not(:animated)").animate({
						scrollTop : destination
					}, 1100)
				}
				return true
			} else {
				return false
			}
		}
	}
})(jQuery);
$(document).ready(function() {
	$("form").validationEngine()
}); 