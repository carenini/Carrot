CREATE SEQUENCE role_class_seq ;
CREATE TABLE role_class (
       class_id integer DEFAULT nextval ('role_class_seq') NOT NULL,
       class_name text DEFAULT '' NOT NULL,
       CONSTRAINT role_class_pkey PRIMARY KEY (class_id),
       CONSTRAINT role_class_name_unique UNIQUE (class_name)
) ;

CREATE SEQUENCE role_seq ;
CREATE TABLE role (
       role_id integer DEFAULT nextval ('role_seq') NOT NULL,
       role_name text DEFAULT '' NOT NULL,
       role_class integer DEFAULT 1 NOT NULL REFERENCES role_class (class_id),
       is_public boolean DEFAULT false NOT NULL,
       CONSTRAINT role_pkey PRIMARY KEY (role_id),
       CONSTRAINT role_name_unique UNIQUE (role_id, role_name)
) ;

CREATE TABLE role_project_refs (
       role_id integer DEFAULT 0 NOT NULL REFERENCES role,
       group_id integer DEFAULT 0 NOT NULL REFERENCES groups,
       CONSTRAINT role_project_refs_unique UNIQUE (role_id, group_id)
) ;

CREATE TABLE role_setting (
       role_id integer DEFAULT 0 NOT NULL REFERENCES role,
       section_name text DEFAULT '' NOT NULL,
       ref_id integer DEFAULT 0 NOT NULL,
       perm_val integer DEFAULT 0 NOT NULL,
       CONSTRAINT role_setting_unique UNIQUE (role_id, section_name, ref_id)
) ;

CREATE TABLE user_role (
       user_id integer DEFAULT 0 NOT NULL REFERENCES users,
       role_id integer DEFAULT 0 NOT NULL REFERENCES role,
       CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
) ;


CREATE SEQUENCE users_pk_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;



CREATE TABLE users (
    user_id integer DEFAULT nextval('users_pk_seq'::text) NOT NULL,
    user_name text DEFAULT ''::text NOT NULL,
    user_pw character varying(32) DEFAULT ''::character varying NOT NULL,
    type_id integer DEFAULT 1,
    unix_gid integer DEFAULT 0
);


CREATE SEQUENCE groups_pk_seq
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;



CREATE TABLE groups (
    group_id integer DEFAULT nextval('groups_pk_seq'::text) NOT NULL,
    group_name character varying(40),
    is_public integer DEFAULT 0 NOT NULL,
    type_id integer DEFAULT 1 NOT NULL,
);


