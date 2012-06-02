################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/src/handwriting.c \
/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/src/jniTest.c 

OBJS += \
./jni/src/handwriting.o \
./jni/src/jniTest.o 

C_DEPS += \
./jni/src/handwriting.d \
./jni/src/jniTest.d 


# Each subdirectory must supply rules for building sources it contributes
jni/src/handwriting.o: /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/src/handwriting.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I"../E:\Android\android-ndk-r6b-windows\android-ndk-r6b\platforms\android-5\arch-arm\usr\include" -I"/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/zinnia-0.06" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

jni/src/jniTest.o: /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/src/jniTest.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I"../E:\Android\android-ndk-r6b-windows\android-ndk-r6b\platforms\android-5\arch-arm\usr\include" -I"/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/jni/zinnia-0.06" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


