package ua.lviv.desctop;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Application {
	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.configure("/META-INF/hibernate.cfg.xml");

		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();
		Session session = config.buildSessionFactory(serviceRegistry).openSession();

		Post post = new Post();
		post.setTitle("some post");

		Comment comment = new Comment();
		comment.setAuthorName("Ivan");
		comment.setPost(post);

		Comment comment1 = new Comment();
		comment1.setAuthorName("Petro");
		comment1.setPost(post);

		Set<Comment> comments = new HashSet<>();
		comments.add(comment);
		comments.add(comment1);

		post.setComment(comments);

		Transaction transaction = session.beginTransaction();
		session.save(post);
		transaction.commit();

		Post postFromDB = (Post) session.get(Post.class, 1);
		System.out.println(postFromDB + "---->" + postFromDB.getComment());

		Comment commentFromDB = (Comment) session.get(Comment.class, 2);
		System.out.println(commentFromDB + "---->" + commentFromDB.getPost());

	}
}
