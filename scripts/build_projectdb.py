#!/usr/bin/env python3
"""Load clean data into PostgreSQL and verify."""

import os
import glob
import psycopg2 as psql


def read_password(password_path):
    """Read PostgreSQL password from secrets file."""
    with open(password_path, "r", encoding="utf-8") as pass_file:
        return pass_file.read().rstrip()


def create_tables(cursor):
    """Execute create_tables.sql to create songs table."""
    with open(os.path.join("sql", "create_tables.sql"), "r", encoding="utf-8") as sql_file:
        cursor.execute(sql_file.read())


def load_csv_files(cursor, copy_command):
    """Load all CSV files from data/clean_data/ into songs table."""
    csv_files = glob.glob("data/clean_data/part-*.csv")
    print(f"Found {len(csv_files)} CSV files")

    for file_path in csv_files:
        print(f"  Loading {file_path}...")
        with open(file_path, "r", encoding="utf-8") as csv_file:
            cursor.copy_expert(copy_command, csv_file)


def verify_data(cursor):
    """Run test queries from test_database.sql."""
    with open(os.path.join("sql", "test_database.sql"), "r", encoding="utf-8") as sql_file:
        for command in sql_file.readlines():
            if command.strip():
                cursor.execute(command)
                rows = cursor.fetchall()
                for row in rows:
                    print(row)


def main():
    """Main function: connect, create table, load data, verify."""
    password = read_password(os.path.join("secrets", ".psql.pass"))
    conn_string = (
        "host=hadoop-04.uni.innopolis.ru port=5432 "
        "user=team23 dbname=team23_projectdb "
        f"password={password}"
    )

    print("Connecting to PostgreSQL...")
    with psql.connect(conn_string) as conn:
        cur = conn.cursor()

        print("Creating tables...")
        create_tables(cur)
        conn.commit()

        print("Loading data from CSV files...")
        with open(
            os.path.join("sql", "import_data.sql"), "r", encoding="utf-8"
        ) as copy_file:
            copy_command = copy_file.read()
        load_csv_files(cur, copy_command)
        conn.commit()

        print("Verifying data...")
        verify_data(cur)


if __name__ == "__main__":
    main()
    