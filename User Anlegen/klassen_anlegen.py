import argparse
import logging
import sys
import openpyxl

logger = logging.getLogger(__name__)
handler = logging.handlers.RotatingFileHandler("create_class.log", maxBytes=10000, backupCount=5)
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
handler.setFormatter(formatter)

stream_handler = logging.StreamHandler(sys.stdout)
stream_handler.setFormatter(formatter)

logger.addHandler(handler)
logger.addHandler(stream_handler)

def create_delete_user(name, create, delete):
    return None


def create_credentials():
    logger.info("Creating credentials sheet")
    workbook = openpyxl.Workbook()
    sheet = workbook.active

    sheet["A1"] = "Username"
    sheet["B1"] = "Password"
    return workbook, sheet


def add_credential(sheet, name, pwd, row):
    logger.info(f"Password for {name} added")
    sheet[f"A{row}"] = name
    sheet[f"B{row}"] = pwd

def create_skripts(file):
    logger.info("Skriptgenerierung startet")

    cred_wb, cred_sh = create_credentials()

    with open("create_user.sh", "w") as create, open("delete_user.sh", "w") as delete:
        print("#!/bin/bash", file=create)
        print("set -e", file=create)
        print("#!/bin/bash", file=delete)
        print("set -e", file=delete)

        print("mkdir /home/klassen", file=create)

        create_delete_user("lehrer", create, delete)
        add_credential(cred_sh, "lehrer", 2, "lehrer")
        create_delete_user("seminar", create, delete)
        add_credential(cred_sh, "seminar", 3, "seminar")

def start_logging(verbose, quiet):
    if quiet:
        logger.setLevel(logging.ERROR)
    elif verbose:
        logger.setLevel(logging.DEBUG)
    else:
        logger.setLevel(logging.INFO)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("filename", type=str, help="filename")
    parser.add_argument("-v", "--verbose", action="store_true", help="activates verbose mode")
    parser.add_argument("-q", "--quiet", action="store_true", help="activates quite mode")
    args = parser.parse_args()

    start_logging(args.verbose, args.quiet)
    try:
        create_skripts(args.filename)
    except FileNotFoundError:
        logger.error("File not found")



if __name__ == "__main__":
    main()