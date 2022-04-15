import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICooperative, Cooperative } from '../cooperative.model';

import { CooperativeService } from './cooperative.service';

describe('Cooperative Service', () => {
  let service: CooperativeService;
  let httpMock: HttpTestingController;
  let elemDefault: ICooperative;
  let expectedResult: ICooperative | ICooperative[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CooperativeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
      localisation: 'AAAAAAA',
      nbAdherents: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Cooperative', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Cooperative()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cooperative', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          localisation: 'BBBBBB',
          nbAdherents: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cooperative', () => {
      const patchObject = Object.assign(
        {
          nom: 'BBBBBB',
          nbAdherents: 1,
        },
        new Cooperative()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cooperative', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
          localisation: 'BBBBBB',
          nbAdherents: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Cooperative', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCooperativeToCollectionIfMissing', () => {
      it('should add a Cooperative to an empty array', () => {
        const cooperative: ICooperative = { id: 123 };
        expectedResult = service.addCooperativeToCollectionIfMissing([], cooperative);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cooperative);
      });

      it('should not add a Cooperative to an array that contains it', () => {
        const cooperative: ICooperative = { id: 123 };
        const cooperativeCollection: ICooperative[] = [
          {
            ...cooperative,
          },
          { id: 456 },
        ];
        expectedResult = service.addCooperativeToCollectionIfMissing(cooperativeCollection, cooperative);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cooperative to an array that doesn't contain it", () => {
        const cooperative: ICooperative = { id: 123 };
        const cooperativeCollection: ICooperative[] = [{ id: 456 }];
        expectedResult = service.addCooperativeToCollectionIfMissing(cooperativeCollection, cooperative);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cooperative);
      });

      it('should add only unique Cooperative to an array', () => {
        const cooperativeArray: ICooperative[] = [{ id: 123 }, { id: 456 }, { id: 31048 }];
        const cooperativeCollection: ICooperative[] = [{ id: 123 }];
        expectedResult = service.addCooperativeToCollectionIfMissing(cooperativeCollection, ...cooperativeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cooperative: ICooperative = { id: 123 };
        const cooperative2: ICooperative = { id: 456 };
        expectedResult = service.addCooperativeToCollectionIfMissing([], cooperative, cooperative2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cooperative);
        expect(expectedResult).toContain(cooperative2);
      });

      it('should accept null and undefined values', () => {
        const cooperative: ICooperative = { id: 123 };
        expectedResult = service.addCooperativeToCollectionIfMissing([], null, cooperative, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cooperative);
      });

      it('should return initial array if no Cooperative is added', () => {
        const cooperativeCollection: ICooperative[] = [{ id: 123 }];
        expectedResult = service.addCooperativeToCollectionIfMissing(cooperativeCollection, undefined, null);
        expect(expectedResult).toEqual(cooperativeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
