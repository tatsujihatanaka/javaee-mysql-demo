package com.example.demo;

import java.io.Serializable; // 追加
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional; // 追加：これが必要です

import com.example.entity.User;

@Named("userBean")
@RequestScoped
public class UserBean implements Serializable { // RequestScopedならSerializable推奨

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    // ★ 追加：登録フォームの入力値を保持するためのオブジェクト
    private User newUser = new User();

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            // ユーザーが見つからない場合は null を返す
            return null;
        }
    }

    @Transactional
    public String save() {
        em.persist(newUser); // これで定義済みの newUser が保存されます
        return "index?faces-redirect=true"; // 一覧画面へ戻る（JSFの場合）
    }

    // ★ 追加：JSPのフォームからアクセスするためにGetter/Setterが必要
    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}