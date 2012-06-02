export XX_VAR3=haha
XX_VAR4=haha

define XX
@echo XX running
endef

default::
	$(XX)

if-target: X = a
if-target:
ifdef X
	@echo X defined
else
	@echo X not defined
endif

set-envvar:
	@echo set-envvar running
	@XX_VAR=haha;export XX_VAR
	@export -p|grep XX_VAR
	@XX_VAR2=haha
	@echo XX_VAR: $(XX_VAR)
	@echo XX_VAR2: $(XX_VAR2)
	@echo SER value:$(SER)
	
show-envvar: set-envvar
	@echo XX_VAR value : $(XX_VAR)
	@echo XX_VAR2 value : $(XX_VAR2)
	@echo XX_VAR3 value : $(XX_VAR3)
	@echo XX_VAR4 value : $(XX_VAR4)
	@echo SER value:$(SER)
	@$(MAKE) -f learn-sub.mk show-envvar2