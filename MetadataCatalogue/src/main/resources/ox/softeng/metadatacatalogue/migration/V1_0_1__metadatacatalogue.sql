--- Core database


SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE SCHEMA IF NOT EXISTS "Core";

ALTER SCHEMA "Core" OWNER TO metadatacatalogue;

SET search_path = "Core", pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE "User" (
	id UUID NOT NULL,
	"First Name" character varying(500),
	"Last Name" character varying(500),
	"Role" int,
	"Email Address" character varying(500) unique,
	"Password" bytea,
	"Salt" bytea
);

CREATE TABLE "UserGroup" (
	id UUID NOT NULL,
	"Name" character varying(500)
);

CREATE TABLE "User_UserGroup"(
	"User Id" UUID NOT NULL,
	"Group Id" UUID NOT NULL
)