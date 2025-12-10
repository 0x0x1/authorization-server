package com.authorization.server.infrastructure.persistence.jpa.constant;

public final class Jpa {

    private Jpa() {}

    /**
     * Table names used in JPA entity mappings.
     */
    public static final class Table {
        private Table() { throw new IllegalStateException("Utility class"); }

        public static final String ACCOUNT = "ACCOUNT";
        public static final String ROLE = "ROLE";
        public static final String PERMISSION = "PERMISSION";
        public static final String PASSWORD = "PASSWORD";
        public static final String USERNAME = "USERNAME";
    }

    /**
     * Standard column names shared across entities.
     */
    public static final class Column {
        private Column() { throw new IllegalStateException("Utility class"); }

        public static final String DISPLAY_NAME = "DISPLAY_NAME";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String LIFECYCLE_STATUS = "LIFECYCLE_STATUS";
        public static final String LOCK_STATUS = "LOCK_STATUS";
        public static final String CREATED_AT = "CREATED_AT";
        public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
        public final static String PASSWORD = "PASSWORD";
        public final static String USERNAME = "USERNAME";
    }

    /**
     * Join table identifiers for many-to-many relationships.
     */
    public static final class JoinTable {
        private JoinTable() { throw new IllegalStateException("Utility class"); }

        public static final String ACCOUNTS_ROLES = "ACCOUNTS_ROLES";
        public static final String ROLES_PERMISSIONS = "ROLES_PERMISSIONS";
    }

    /**
     * Foreign key column identifiers.
     */
    public static final class ForeignKey {
        private ForeignKey() { throw new IllegalStateException("Utility class"); }

        public static final String ACCOUNT_ID = "ACCOUNT_ID";
        public static final String ROLE_ID = "ROLE_ID";
        public static final String PERMISSION_ID = "PERMISSION_ID";
    }

//    public static final class EntityConstants {
//        private EntityConstants() {
//            throw new IllegalStateException("Utility class");
//        }
//
//        public final static String DISPLAY_NAME = "DISPLAY_NAME";
//        public final static String DESCRIPTION = "DESCRIPTION";
//        public final static String ROLE_PERMISSION = "ROLES_PERMISSIONS";
//        public final static String ACCOUNT_ID = "ACCOUNT_ID";
//        public final static String ACCOUNTS_ROLES = "ACCOUNTS_ROLES";
//        public final static String ROLE_ID = "ROLE_ID";
//        public final static String PERMISSION_ID = "PERMISSION_ID";
//        public final static String LIFECYCLE_STATUS = "LIFECYCLE_STATUS";
//        public final static String LOCK_STATUS = "LOCK_STATUS";
//        public final static String CREATED_AT = "CREATED_AT";
//        public final static String EMAIL_ADDRESS = "EMAIL_ADDRESS";
//        public final static String ACCOUNT = "ACCOUNT";
//        public final static String ROLE = "ROLE";
//        public final static String PERMISSION = "PERMISSION";
//        public final static String PASSWORD = "PASSWORD";
//        public final static String USERNAME = "USERNAME";
//    }
}
