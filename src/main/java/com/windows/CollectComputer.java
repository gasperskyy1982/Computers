package com.windows;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class CollectComputer {

	private String cpuTemp = "";
	private String chipsetTemp = "";
	private String socketTemp = "";

	private Asus asus = new Asus();
	private AMD amd = new AMD();
	private MSI msi = new MSI();
	private Intel intel = new Intel();

	private Class<?> asusClazz = asus.getClass();
	private Class<?> amdClazz = amd.getClass();
	private Class<?> msiClazz = msi.getClass();
	private Class<?> intelClazz = intel.getClass();

	private Field[] asusField = asusClazz.getFields();
	private Field[] amdField = amdClazz.getFields();
	private Field[] msiField = msiClazz.getFields();
	private Field[] intelField = intelClazz.getFields();

	public CollectComputer() {
		try {
			tryAmd();
			tryIntel();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	
	// ---------------Подбор материнских плат по процессорам AMD ---------------
		
		public void tryAmd() throws IllegalArgumentException, IllegalAccessException {

			if (amdClazz.isAnnotationPresent(CPU.class)) {
				CPU cpu = amdClazz.getAnnotation(CPU.class);
				cpuTemp = cpu.socket();
				System.out.println("Сокет процессоров AMD: " + cpuTemp);
			} else {
				System.out.println("No annotation");
			}

			if (msiClazz.isAnnotationPresent(Socket.class)) {
				Socket socket = msiClazz.getAnnotation(Socket.class);
				socketTemp = socket.name();
				System.out.println("Сокет материнских плат MSI: " + socketTemp + "\n");
			}

			if (cpuTemp.equals(socketTemp)) {

				for (Field cpu : amdField) {
					for (Field chp : msiField) {
						if (chp.isAnnotationPresent(Chipset.class)) {
							chipsetTemp = chp.getAnnotation(Chipset.class).type();
							if (chipsetTemp.equals(cpu.get(amd))) {
								System.out.println("Подходящая для сборки комплектация:");
								System.out.println("Материнская плата: " + chp.getName() + " - " + chp.get(msi));
								System.out.println("Процессор:" + cpu.get(amd) + "\n");
							}
						}
					}
				}

			} else {

				if (asusClazz.isAnnotationPresent(Socket.class)) {
					Socket socket = asusClazz.getAnnotation(Socket.class);
					socketTemp = socket.name();
					System.out.println("Сокет материнских плат Asus: " + socketTemp + "\n");
				}

				if (cpuTemp.equals(socketTemp)) {

					for (Field cpu : amdField) {
						for (Field chp : asusField) {
							if (chp.isAnnotationPresent(Chipset.class)) {
								chipsetTemp = chp.getAnnotation(Chipset.class).type();
								if (chipsetTemp.equals(cpu.get(amd))) {
									System.out.println("Подходящая для сборки комплектация:");
									System.out.println("Материнская плата: " + chp.getName() + " - " + chp.get(asus));
									System.out.println("Процессор:" + cpu.get(amd) + "\n");
								}
							}
						}
					}
				}
			}
		}


	// ---------------Подбор материнских плат по процессорам Intel ---------------

		
		public void tryIntel() throws IllegalArgumentException, IllegalAccessException {

			if (intelClazz.isAnnotationPresent(CPU.class)) {
				CPU cpu = intelClazz.getAnnotation(CPU.class);
				cpuTemp = cpu.socket();
				System.out.println("Сокет процессоров Intel: " + cpuTemp);
			} else {
				System.out.println("No annotation");
			}

		if (cpuTemp.equals(socketTemp)) {	
			
			if (msiClazz.isAnnotationPresent(Socket.class)) {
				Socket socket = msiClazz.getAnnotation(Socket.class);
				socketTemp = socket.name();
				System.out.println("Сокет материнских плат MSI: " + socketTemp + "\n");
			}

			if (cpuTemp.equals(socketTemp)) {

				for (Field cpu : intelField) {
					for (Field chp : msiField) {
						if (chp.isAnnotationPresent(Chipset.class)) {
							chipsetTemp = chp.getAnnotation(Chipset.class).type();
							if (chipsetTemp.equals(cpu.get(intel))) {
								System.out.println("Подходящая для сборки комплектация:");
								System.out.println("Материнская плата: " + chp.getName() + " - " + chp.get(msi));
								System.out.println("Процессор:" + cpu.get(intel) + "\n");
							}
						}
					}
				}
			}
			} else {

				if (asusClazz.isAnnotationPresent(Socket.class)) {
					Socket socket = asusClazz.getAnnotation(Socket.class);
					socketTemp = socket.name();
					System.out.println("Сокет материнских плат Asus: " + socketTemp + "\n");
				}

				if (cpuTemp.equals(socketTemp)) {

					for (Field cpu : intelField) {
						for (Field chp : asusField) {
							if (chp.isAnnotationPresent(Chipset.class)) {
								chipsetTemp = chp.getAnnotation(Chipset.class).type();
								if (chipsetTemp.equals(cpu.get(intel))) {
									System.out.println("Подходящая для сборки комплектация:");
									System.out.println("Материнская плата: " + chp.getName() + " - " + chp.get(asus));
									System.out.println("Процессор:" + cpu.get(intel) + "\n");
								}
							}
						}
					}
				}
			}
		}

}