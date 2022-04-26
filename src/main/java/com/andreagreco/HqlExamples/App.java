package com.andreagreco.HqlExamples;

import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class App 
{
    public static void main( String[] args )
    {
        Configuration con = new Configuration().configure()
        		.addAnnotatedClass(Student.class);
        ServiceRegistry reg = new ServiceRegistryBuilder()
        		.applySettings(con.getProperties()).buildServiceRegistry();
        SessionFactory sf = con.buildSessionFactory(reg);
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        
//		Let's populate the database        
//        Random r = new Random();
//        for(int i = 1; i <= 50; i++) {
//        	Student s = new Student();
//        	s.setRollno(i);
//        	s.setName("Student " + i);
//        	s.setMarks(r.nextInt(40) + 60);
//        	session.save(s);
//        }
        
        Query q = session.createQuery("from Student where marks > 70");
        List<Student> students = q.list();
        for (Student s : students) {
        	System.out.println(s);
        }
        
        System.out.println("*****");
        
        q = session.createQuery("from Student where rollno = 7");
        Student s = (Student) q.uniqueResult();
        System.out.println(s);
        
        System.out.println("*****");
        
        q = session.createQuery("select rollno, name from Student where rollno = 7");
        Object[] student = (Object[]) q.uniqueResult();
//        for(Object o : student) {
//        	System.out.print(o + " ");
//        }
        System.out.println(student[0] + ": " + student[1]);
        
        System.out.println("*****");
        
        q = session.createQuery("select name, marks from Student");
        List<Object[]> sts = (List<Object[]>) q.list();
        for (Object[] sObj : sts) {
        	System.out.println(sObj[0] + " : " + sObj[1]);
        }
        
        System.out.println("*****");
        
        q = session.createQuery("select avg(marks) from Student");
        double result = (Double) q.uniqueResult();
        System.out.println("Avarege: " + result);
        
        System.out.println("*****");
        
        q = session.createQuery("select max(marks) from Student");
        int res = (Integer) q.uniqueResult();
        System.out.println("Max: " + res);
        
        System.out.println("*****");
        
        q = session.createQuery("select name from Student where rollno = ?");
        q.setParameter(0, 4);
        String str = (String) q.uniqueResult();
        System.out.println(str);
                
        tx.commit();
        session.close();
    }
}
