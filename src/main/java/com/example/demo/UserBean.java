package com.example.demo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.entity.User;

@Named("userBean") // JSPから ${userBean} で呼べるようにする魔法のアノテーション
@RequestScoped     // 1回のリクエスト（画面表示）の間だけ生存する設定
public class UserBean {

    @PersistenceContext(unitName = "myPU") // persistence.xml の persistence-unit name と合わせる
    private EntityManager em;

    /**
     * DBから全ユーザーを取得してリストで返すメソッド
     */
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}