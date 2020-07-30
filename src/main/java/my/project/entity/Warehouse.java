package my.project.entity;

final public class Warehouse {

    private int goods;

    public Warehouse(int goods)
    {
        this.goods = goods;
    }

    synchronized public int getGoods() {
        return goods;
    }

    synchronized public int sellGoods(int count)
    {
        int sold = count > goods ? goods : count;
        goods -= sold;

        return sold;
    }

    synchronized public boolean isEmpty()
    {
        return goods <= 0;
    }
}
