package com.alexneo.drugsbase;

public class Drug {
    // из-за статика это
    // статик это тип переменных, которые относятся не к обьекту, а к классу.
    // сейчас приведу пример
    public int id;
    public String name;
    public String description;
    // каи использовать
    public String usage;
    // что вызывает
    public String affect;
    // побочный эффект
    public String cautions;
    // уровень зависимости
    public AddictionLevel addiction;
    // цена в шкурках
    public int price;
    // картинка
    public String cover;

    public Drug(int id, String name, String description, String usage, String affect, String cautions, AddictionLevel addiction, int price, String cover) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.affect = affect;
        this.cautions = cautions;
        this.addiction = addiction;
        this.price = price;
        this.cover = cover;
    }

}