// Внутренним классом называют класс, который является членом другого класса.

public class OuterClass {
	
	private String privateField = "Приватное поле внешнего класса.";
	
	// Статический блок инициализации.
	static {
	}
	
	// Вложенный внутренний класс может иметь любой модификатор доступа 
	// (private, package—private (по умолчанию, когда не указан модификатор), protected, public).
	class NestedInnerClass {
		public void show() {
			System.out.println("Метод внутреннего класса.");
		}
		
		// Вложенный внутренний класс может получить доступ 
		// к любому приватному полю или методу экземпляра внешнего класса.
		public void getOuterPrivateField(OuterClass outer) {
			System.out.println(outer.privateField);
		}
		
		// Вложенный внутренний класс не может содержать в себе статических методов или статических полей. 
		// Это связано с тем что, внутренний класс неявно связан с объектом своего внешнего класса, 
		// поэтому он не может объявлять никаких статических методов внутри себя.
	}
	
	// Так же как и классы, интерфейсы могут быть вложенными и иметь модификаторы доступа.
	public interface InnerInterface {
		public String hello = "Hello";
		public void sayHello();
	}
	
	// Статические вложенные классы технически не являются внутренними классами.
	// По сути, они представляют собой члены внешнего класса.
	static class StaticNestedClass {
		public void show() {
			System.out.println("Метод статического внутреннего класса.");
		}
	}
	
	void outerMethod() {
		System.out.println("Метод внешнего класса.");
		
		// Внутренний класс может быть объявлен внутри метода.
		
		// Внутренний класс в локальном методе не может использовать локальные переменные внешнего метода до тех пор, 
		// пока локальная переменная не будет объявлена как финальная (final).
		// Основная причина, по которой необходимо объявлять локальную переменную как финальную заключается в том что, 
		// локальная переменная живёт в стеке до тех пор, пока метод находится в стеке. 
		// А  в случае использования внутреннего класса возможна ситуация, когда экземпляр внутреннего класса живёт в куче и после выхода из метода, 
		// но ему может быть необходим доступ к переменной, объявленной в методе. Для этого, компилятор может сохранить копию локальной переменной, 
		// которая объявлена как финальная, в поле внутреннего класса для дальнейшего использования.

		// Внутренний класс в локальном методе не может быть помечен как private, protected, static и transient, 
		// но может быть помечен как abstract и final, но не оба одновременно.

		class MethodLocalInnerClass {
			public void show() {
				System.out.println("Метод внутреннего класса объвленного внутри метода.");
			}
		}
		MethodLocalInnerClass inner = new MethodLocalInnerClass();
		inner.show();
	}
	
	static Demo demo = new Demo() {
		@Override
		public void show() {
			super.show();
			System.out.println("Метод внутреннего анонимного класса.");
		}
	};
	
	// Анонимные внутренние классы объявляются без указания имени класса.
	// Они могут быть созданы двумя путями:
	
	// Как наследник определённого класса:
	static Hello h = new Hello() {
		public void show() {
			System.out.println("Метод внутреннего анонимного класса.");
		}
	};
	
	// Как реализация определённого интерфейса:
	public static void main(String[] args) {
		OuterClass outer = new OuterClass();
		outer.outerMethod();
		
		OuterClass.NestedInnerClass inner = new OuterClass().new NestedInnerClass();
		inner.show();
		
		OuterClass.NestedInnerClass inner2 = outer.new NestedInnerClass();
		inner2.getOuterPrivateField(outer);
		
		OuterClass.StaticNestedClass nestedStatic = new OuterClass.StaticNestedClass();
		nestedStatic.show();
		
		demo.show();
		h.show();
	}
}

class Demo {
	public void show() {
		System.out.println("Метод show() класса Demo.");
	}
}

interface Hello {
	void show();
}

