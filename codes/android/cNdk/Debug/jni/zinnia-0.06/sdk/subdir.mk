################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
/home/he/sw/android/eclipse-java-indigo-SR1-linux-gtk/codes/natviewer/android/useNdk/jni/zinnia-0.06/sdk/example.c 

OBJS += \
./jni/zinnia-0.06/sdk/example.o 

C_DEPS += \
./jni/zinnia-0.06/sdk/example.d 


# Each subdirectory must supply rules for building sources it contributes
jni/zinnia-0.06/sdk/example.o: /home/he/sw/android/eclipse-java-indigo-SR1-linux-gtk/codes/natviewer/android/useNdk/jni/zinnia-0.06/sdk/example.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I"E:\Android\android-ndk-r6b-windows\android-ndk-r6b\platforms\android-5\arch-arm\usr\include" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


