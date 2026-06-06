package com.java11.design.decorator;

/**
 * 装饰者模式演示
 * 
 * 装饰者模式允许在不修改原有对象的情况下，动态地给对象添加新的行为。
 * 它通过创建包装对象（即装饰者）来包裹真实对象，从而提供额外的功能。
 * 
 * 应用场景：
 * 1. Java I/O 流（BufferedReader、InputStreamReader等）
 * 2. 需要动态添加功能的场景
 * 3. 避免类爆炸（使用继承会导致子类数量激增）
 */
public class DecoratorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== 装饰者模式演示 ===\n");
        
        // 创建一个基础咖啡
        Coffee coffee = new SimpleCoffee();
        System.out.println("基础咖啡: " + coffee.getDescription());
        System.out.println("价格: ¥" + coffee.getCost());
        System.out.println();
        
        // 添加牛奶装饰
        Coffee milkCoffee = new MilkDecorator(coffee);
        System.out.println("加牛奶的咖啡: " + milkCoffee.getDescription());
        System.out.println("价格: ¥" + milkCoffee.getCost());
        System.out.println();
        
        // 添加糖装饰
        Coffee sugarMilkCoffee = new SugarDecorator(milkCoffee);
        System.out.println("加牛奶和糖的咖啡: " + sugarMilkCoffee.getDescription());
        System.out.println("价格: ¥" + sugarMilkCoffee.getCost());
        System.out.println();
        
        // 添加摩卡装饰
        Coffee mochaSugarMilkCoffee = new MochaDecorator(sugarMilkCoffee);
        System.out.println("加摩卡、牛奶和糖的咖啡: " + mochaSugarMilkCoffee.getDescription());
        System.out.println("价格: ¥" + mochaSugarMilkCoffee.getCost());
        System.out.println();
        
        // 另一种组合：直接加摩卡和牛奶
        Coffee mochaMilkCoffee = new MochaDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println("加摩卡和牛奶的咖啡: " + mochaMilkCoffee.getDescription());
        System.out.println("价格: ¥" + mochaMilkCoffee.getCost());
    }
}

/**
 * 组件接口 - 定义咖啡的基本行为
 */
interface Coffee {
    String getDescription();
    double getCost();
}

/**
 * 具体组件 - 基础咖啡实现
 */
class SimpleCoffee implements Coffee {
    
    @Override
    public String getDescription() {
        return "基础咖啡";
    }
    
    @Override
    public double getCost() {
        return 10.0;
    }
}

/**
 * 抽象装饰者 - 实现Coffee接口并持有Coffee引用
 */
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

/**
 * 具体装饰者 - 牛奶装饰
 */
class MilkDecorator extends CoffeeDecorator {
    
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + " + 牛奶";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 3.0;
    }
}

/**
 * 具体装饰者 - 糖装饰
 */
class SugarDecorator extends CoffeeDecorator {
    
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + " + 糖";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 1.5;
    }
}

/**
 * 具体装饰者 - 摩卡装饰
 */
class MochaDecorator extends CoffeeDecorator {
    
    public MochaDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + " + 摩卡";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 5.0;
    }
}
