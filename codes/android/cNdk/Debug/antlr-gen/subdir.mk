################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/antlr/gen/c/JpVocabularyLexer.c \
/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/antlr/gen/c/JpVocabularyParser.c 

OBJS += \
./antlr-gen/JpVocabularyLexer.o \
./antlr-gen/JpVocabularyParser.o 

C_DEPS += \
./antlr-gen/JpVocabularyLexer.d \
./antlr-gen/JpVocabularyParser.d 


# Each subdirectory must supply rules for building sources it contributes
antlr-gen/JpVocabularyLexer.o: /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/antlr/gen/c/JpVocabularyLexer.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I"../E:\Android\android-ndk-r6b-windows\android-ndk-r6b\platforms\android-5\arch-arm\usr\include" -I/home/he/antlr/build/include -I"/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/zinnia-0.06/cpp" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

antlr-gen/JpVocabularyParser.o: /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/antlr/gen/c/JpVocabularyParser.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I"../E:\Android\android-ndk-r6b-windows\android-ndk-r6b\platforms\android-5\arch-arm\usr\include" -I/home/he/antlr/build/include -I"/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/zinnia-0.06/cpp" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


